package com.senolkarakurt.purchaseservice.service.impl;

import com.senolkarakurt.dto.request.CustomerPackageRequestDto;
import com.senolkarakurt.dto.request.CustomerRequestDto;
import com.senolkarakurt.dto.request.PackageRequestDto;
import com.senolkarakurt.dto.response.*;
import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.purchaseservice.client.service.CustomerClientService;
import com.senolkarakurt.purchaseservice.client.service.OrderClientService;
import com.senolkarakurt.purchaseservice.client.service.PackageClientService;
import com.senolkarakurt.purchaseservice.client.service.UserClientService;
import com.senolkarakurt.purchaseservice.converter.*;
import com.senolkarakurt.purchaseservice.dto.request.PurchaseSaveRequestDto;
import com.senolkarakurt.purchaseservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.purchaseservice.model.*;
import com.senolkarakurt.purchaseservice.model.CPackage;
import com.senolkarakurt.purchaseservice.producer.NotificationCustomerPackageRequestProducer;
import com.senolkarakurt.purchaseservice.producer.NotificationInvoiceProducer;
import com.senolkarakurt.purchaseservice.producer.dto.NotificationCustomerPackageRequestDto;
import com.senolkarakurt.purchaseservice.producer.dto.NotificationInvoiceDto;
import com.senolkarakurt.purchaseservice.repository.InvoiceRepository;
import com.senolkarakurt.purchaseservice.repository.PurchaseRepository;
import com.senolkarakurt.purchaseservice.service.PurchaseService;
import com.senolkarakurt.util.GenerateRandomUnique;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final InvoiceRepository invoiceRepository;
    private final OrderClientService orderClientService;
    private final CustomerClientService customerClientService;
    private final UserClientService userClientService;
    private final PackageClientService packageClientService;
    private final ExceptionMessagesResource exceptionMessagesResource;
    private final NotificationInvoiceProducer notificationInvoiceProducer;
    private final NotificationCustomerPackageRequestProducer notificationCustomerPackageRequestProducer;

    @Override
    public void save(PurchaseSaveRequestDto purchaseSaveRequestDto) {
        Purchase purchase = purchaseRepository.save(purchaseSaveRequestDto.getPurchase());
        Invoice invoice = Invoice.builder()
                .createDateTime(LocalDateTime.now())
                .purchaseId(purchase.getId())
                .totalPrice(purchase.getTotalPrice())
                .invoiceNo(GenerateRandomUnique.createRandomInvoiceNo())
                .build();
        invoice = invoiceRepository.save(invoice);
        InvoiceResponseDto invoiceResponseDto = getInvoiceResponseDto(invoice);
        NotificationInvoiceDto notificationInvoiceDto = InvoiceConverter.toNotificationDtoByNotificationTypeAndInvoice(NotificationType.EMAIL, invoiceResponseDto);
        notificationInvoiceProducer.sendNotificationInvoice(notificationInvoiceDto);

        CustomerPackageRequestDto customerPackageRequestDto = getCustomerPackageRequestDto(purchase);
        NotificationCustomerPackageRequestDto notificationCustomerPackageRequestDto = NotificationCustomerPackageRequestDto.builder()
                .customerPackageRequestDto(customerPackageRequestDto)
                .build();
        notificationCustomerPackageRequestProducer.sendNotificationCustomerPackageRequest(notificationCustomerPackageRequestDto);
    }

    @Override
    public List<PurchaseResponseDto> getAll() {
        List<PurchaseResponseDto> purchaseResponseDtoList = new ArrayList<>();
        purchaseRepository.findAll().forEach(purchase -> {
            PurchaseResponseDto purchaseResponseDto = PurchaseConverter.toPurchaseResponseDtoByPurchase(purchase);
            Order order = orderClientService.getOrderById(purchase.getOrderId());
            OrderResponseDto orderResponseDto = getOrderResponseDto(order);
            purchaseResponseDto.setOrderResponseDto(orderResponseDto);
            purchaseResponseDtoList.add(purchaseResponseDto);
        });
        return purchaseResponseDtoList;
    }

    @Override
    public PurchaseResponseDto getById(Long id) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);
        if (purchaseOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPurchaseNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getPurchaseNotFoundWithId());
        }
        Purchase purchase = purchaseOptional.get();
        Order order = orderClientService.getOrderById(purchase.getOrderId());
        OrderResponseDto orderResponseDto = getOrderResponseDto(order);
        PurchaseResponseDto purchaseResponseDto = PurchaseConverter.toPurchaseResponseDtoByPurchase(purchase);
        purchaseResponseDto.setOrderResponseDto(orderResponseDto);
        return purchaseResponseDto;
    }

    @Override
    public Purchase getPurchaseById(Long id) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);
        return purchaseOptional.orElse(null);
    }

    private CustomerPackageRequestDto getCustomerPackageRequestDto(Purchase purchase){
        Order order = orderClientService.getOrderById(purchase.getOrderId());
        CPackage CPackageById = packageClientService.getPackageById(order.getPackageId());
        PackageRequestDto packageRequestDto = PackageRequestDto.builder()
                .id(CPackageById.getId())
                .build();
        Customer customer = customerClientService.getCustomerById(order.getCustomerId());
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .id(customer.getId())
                .build();
        return CustomerPackageRequestDto.builder()
                .packageRequestDto(packageRequestDto)
                .customerRequestDto(customerRequestDto)
                .build();
    }

    private InvoiceResponseDto getInvoiceResponseDto(Invoice invoice){
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(invoice.getPurchaseId());
        if (purchaseOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPurchaseNotFoundWithId(), invoice.getPurchaseId()));
            throw new CommonException(exceptionMessagesResource.getPurchaseNotFoundWithId());
        }
        Purchase purchase = purchaseOptional.get();
        Order order = orderClientService.getOrderById(purchase.getOrderId());
        OrderResponseDto orderResponseDto = getOrderResponseDto(order);
        InvoiceResponseDto invoiceResponseDto = InvoiceConverter.toInvoiceResponseDtoByInvoice(invoice);
        PurchaseResponseDto purchaseResponseDto = PurchaseConverter.toPurchaseResponseDtoByPurchase(purchase);
        purchaseResponseDto.setOrderResponseDto(orderResponseDto);
        invoiceResponseDto.setPurchaseResponseDto(purchaseResponseDto);
        return invoiceResponseDto;
    }

    private OrderResponseDto getOrderResponseDto(Order order){
        OrderResponseDto orderResponseDto = OrderConverter.toOrderResponseDtoByOrder(order);
        Customer customer = customerClientService.getCustomerById(order.getCustomerId());
        CustomerResponseDto customerResponseDto = CustomerConverter.toCustomerResponseDtoByCustomer(customer);
        User user = userClientService.getUserById(customer.getUserId());
        UserResponseDto userResponseDto = UserConverter.toUserResponseDtoByUser(user);
        Set<Address> addresses = userClientService.getAddressesByUserId(user.getId());
        Set<AddressResponseDto> addressResponseDtoSet = AddressConverter.toSetAddressesResponseDtoBySetAddresses(addresses);
        userResponseDto.setAddressResponseDtoSet(addressResponseDtoSet);
        customerResponseDto.setUserResponseDto(userResponseDto);
        orderResponseDto.setCustomerResponseDto(customerResponseDto);
        CPackage CPackageById = packageClientService.getPackageById(order.getPackageId());
        PackageResponseDto packageResponseDto = PackageConverter.toPackageResponseDtoByPackage(CPackageById);
        orderResponseDto.setPackageResponseDto(packageResponseDto);
        return orderResponseDto;
    }
}

package com.senolkarakurt.orderservice.service.impl;

import com.senolkarakurt.dto.request.OrderRequestDto;
import com.senolkarakurt.dto.response.*;
import com.senolkarakurt.enums.AccountType;
import com.senolkarakurt.enums.OrderStatus;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.orderservice.client.service.*;
import com.senolkarakurt.orderservice.converter.*;
import com.senolkarakurt.orderservice.dto.request.OrderSaveRequestDto;
import com.senolkarakurt.orderservice.dto.request.OrderSearchRequestDto;
import com.senolkarakurt.orderservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.orderservice.model.*;
import com.senolkarakurt.orderservice.model.CPackage;
import com.senolkarakurt.orderservice.repository.OrderRepository;
import com.senolkarakurt.orderservice.repository.specification.OrderSpecification;
import com.senolkarakurt.orderservice.service.OrderService;
import com.senolkarakurt.util.GenerateRandomUnique;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClientService customerClientService;
    private final UserClientService userClientService;
    private final PackageClientService packageClientService;
    private final PurchaseClientService purchaseClientService;
    private final ExceptionMessagesResource exceptionMessagesResource;

    @Override
    public void save(OrderRequestDto orderRequestDto) {
        OrderSaveRequestDto orderSaveRequestDto = getOrderSaveRequestDto(orderRequestDto);
        Order order = OrderConverter.toOrderByOrderSaveRequestDto(orderSaveRequestDto);
        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        order = orderRepository.save(order);
        changeCustomerAccountTypeByOrder(order);

        CPackage cPackageById = packageClientService.getPackageById(order.getPackageId());
        Purchase purchase = Purchase.builder()
                .createDateTime(LocalDateTime.now())
                .totalPrice(cPackageById.getPrice())
                .orderId(order.getId())
                .build();
        purchaseClientService.save(purchase);
    }

    @Override
    public List<OrderResponseDto> getAllByCustomerId(Long customerId) {
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        orderRepository.findAllByCustomerId(customerId).forEach(order -> {
            orderResponseDtoList.add(getOrderResponseDto(order));
        });
        return orderResponseDtoList;
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getOrderNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getOrderNotFoundWithId());
        }
        if (!orderOptional.get().getRecordStatus().equals(RecordStatus.ACTIVE)){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getOrderNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getOrderNotFoundWithId());
        }
        return orderOptional.orElse(null);
    }

    @Override
    public List<OrderResponseDto> search(OrderSearchRequestDto orderSearchRequestDto) {

        Specification<Order> orderSpecification = Specification.where(null);
        if (orderSearchRequestDto.getOrderCode() != null){
            orderSpecification = orderSpecification.and(OrderSpecification.hasOrderCode(orderSearchRequestDto.getOrderCode()));
        }
        if (orderSearchRequestDto.getOrderStatus() != null){
            orderSpecification = orderSpecification.and(OrderSpecification.hasOrderStatus(orderSearchRequestDto.getOrderStatus()));
        }
        if (orderSearchRequestDto.getCreateDate() != null){
            orderSpecification = orderSpecification.and(OrderSpecification.hasCreateDateTime(orderSearchRequestDto.getCreateDate()));
        }

        PageRequest pageRequest = PageRequest.of(
                orderSearchRequestDto.getPage(),
                orderSearchRequestDto.getSize(),
                Sort.by(orderSearchRequestDto.getSort().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC
                        , "orderCode")
        );
        Page<Order> orders = orderRepository.findAll(orderSpecification, pageRequest);
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        if (orders != null){
            orders.stream().toList().forEach(order -> {
                orderResponseDtoList.add(getOrderResponseDto(order));
            });
        }
        return orderResponseDtoList;
    }

    private void changeCustomerAccountTypeByOrder(Order order){
        CPackage cPackageById = packageClientService.getPackageById(order.getPackageId());
        if (cPackageById.getPrice() == null ||
        cPackageById.getPrice().compareTo(BigDecimal.ZERO) < 0){
            log.error("%s : {}".formatted(exceptionMessagesResource.getPackagePriceDoingControl()));
            throw new CommonException(exceptionMessagesResource.getPackagePriceDoingControl());
        }
        Integer score = cPackageById.getPrice().multiply(BigDecimal.valueOf(2))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).intValue();

        Customer customer = customerClientService.getCustomerById(order.getCustomerId());
        Integer totalScore = customer.getScore() + score;

        AccountType accountType = customer.getAccountType();
        if (totalScore.compareTo(1000) > 0){
            accountType = AccountType.SILVER;
        } else if (totalScore.compareTo(2000) > 0) {
            accountType = AccountType.GOLD;
        } else if (totalScore.compareTo(4000) > 0) {
            accountType = AccountType.PLATINUM;
        }
        customerClientService.changeAccountTypeAndScore(customer, accountType, totalScore);
    }

    private OrderSaveRequestDto getOrderSaveRequestDto(OrderRequestDto orderRequestDto){
        Customer customer = customerClientService.getCustomerById(orderRequestDto.getCustomerRequestDto().getId());
        CPackage aCPackage = packageClientService.getPackageById(orderRequestDto.getPackageRequestDto().getId());
        String orderCode = createOrderCode(GenerateRandomUnique.createRandomOrderCode());
        return OrderSaveRequestDto.builder()
                .orderCode(orderCode)
                .aCPackage(aCPackage)
                .customer(customer)
                .build();
    }

    private String createOrderCode(String orderCode){
        String newOrderCode = orderCode;
        Optional<Order> orderOptional = orderRepository.findAll().stream()
                .filter(order -> order.getOrderCode().equals(orderCode))
                .findFirst();
        if (orderOptional.isPresent()){
            newOrderCode = GenerateRandomUnique.createRandomOrderCode();
            createOrderCode(newOrderCode);
        }
        return newOrderCode;
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

package com.senolkarakurt.orderservice.service;

import com.senolkarakurt.dto.request.*;
import com.senolkarakurt.dto.response.OrderResponseDto;
import com.senolkarakurt.enums.OrderStatus;
import com.senolkarakurt.enums.PackageType;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.orderservice.client.service.CustomerClientService;
import com.senolkarakurt.orderservice.client.service.PackageClientService;
import com.senolkarakurt.orderservice.client.service.PurchaseClientService;
import com.senolkarakurt.orderservice.client.service.UserClientService;
import com.senolkarakurt.orderservice.dto.request.OrderSearchRequestDto;
import com.senolkarakurt.orderservice.model.*;
import com.senolkarakurt.orderservice.repository.OrderRepository;
import com.senolkarakurt.orderservice.repository.specification.OrderSpecification;
import com.senolkarakurt.orderservice.service.impl.OrderServiceImpl;
import com.senolkarakurt.util.GenerateRandomUnique;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private PackageClientService packageClientService;

    @Mock
    private CustomerClientService customerClientService;

    @Mock
    private UserClientService userClientService;

    @Mock
    private PurchaseClientService purchaseClientService;

    @Test
    public void testGetOrderListByCustomerId() {
        Order order1 = prepareOrder1();
        Order order2 = prepareOrder2();
        when(orderRepository.findAllByCustomerId(Mockito.any())).thenReturn(Arrays.asList(order1, order2));
        when(customerClientService.getCustomerById(Mockito.any())).thenReturn(new Customer());
        when(userClientService.getUserById(Mockito.any())).thenReturn(new User());
        when(userClientService.getAddressesByUserId(Mockito.any())).thenReturn(new HashSet<>());
        when(packageClientService.getPackageById(Mockito.any())).thenReturn(prepareCPackage());
        List<OrderResponseDto> orderResponseDtoList = orderService.getAllByCustomerId(Mockito.any());
        assertEquals(orderResponseDtoList.size(), 2);
        assertEquals(orderResponseDtoList.get(0).getOrderStatus(), OrderStatus.INITIAL);
        assertEquals(orderResponseDtoList.get(1).getOrderStatus(), OrderStatus.INITIAL);
    }

    @Test
    public void testGetOrderById() {
        Order order1 = prepareOrder1();
        when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order1));
        Order order = orderService.getOrderById(Mockito.any());
        assertNotEquals(order, null);
        assertEquals(order.getOrderStatus(), OrderStatus.INITIAL);
        assertNotEquals(order.getOrderCode(), "ORD7800947");
    }

    @Test
    public void testGetInvalidOrderById() {
        when(orderRepository.findById(Mockito.any())).thenThrow(new CommonException("Bu id ile kay?tl? sipari? bulunamad?"));
        Exception exception = assertThrows(CommonException.class, () -> {
            orderService.getOrderById(Mockito.any());
        });
        assertTrue(exception.getMessage().contains("Bu id ile kay?tl? sipari? bulunamad?"));
    }

    @Test
    public void testCreateOrder() {
        when(customerClientService.getCustomerById(Mockito.any())).thenReturn(prepareCustomer());
        when(userClientService.getUserById(Mockito.any())).thenReturn(prepareUser());
        when(userClientService.getAddressesByUserId(Mockito.any())).thenReturn(new HashSet<>());
        when(packageClientService.getPackageById(Mockito.any())).thenReturn(prepareCPackage());
        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(Instancio.create(Order.class));
        orderService.save(prepareOrderRequestDto());
        verify(orderRepository, times(1)).save(Mockito.any(Order.class));
    }

    @Test
    public void testSearch(){
        when(customerClientService.getCustomerById(Mockito.any())).thenReturn(prepareCustomer());
        when(userClientService.getUserById(Mockito.any())).thenReturn(prepareUser());
        when(userClientService.getAddressesByUserId(Mockito.any())).thenReturn(new HashSet<>());
        when(packageClientService.getPackageById(Mockito.any())).thenReturn(prepareCPackage());

        OrderSearchRequestDto orderSearchRequestDto = prepareOrderSearchRequestDto();

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
                        , "createDateTime")
        );

        List<Order> orderList = List.of(prepareOrder1(), prepareOrder2());
        Page<Order> orderPageList = new PageImpl<>(orderList,pageRequest,1);
        when(orderRepository.findAllByCustomerId(orderSearchRequestDto.getCustomerRequestDto().getId(), orderSpecification,pageRequest)).thenReturn(orderPageList);
        Page<Order> newOrderPageList = orderRepository.findAllByCustomerId(orderSearchRequestDto.getCustomerRequestDto().getId(), orderSpecification, pageRequest);
        List<OrderResponseDto> orderResponseDtoList = orderService.search(orderSearchRequestDto);
        assertEquals(newOrderPageList.getTotalElements(), orderPageList.getTotalElements());
        assertThat(orderResponseDtoList).isEmpty();
    }

    private OrderRequestDto prepareOrderRequestDto(){
        CustomerRequestDto customerRequestDto = prepareCustomerRequestDto();
        UserRequestDto userRequestDto = prepareUserRequestDto();
        userRequestDto.setAddressRequestDtoSet(prepareSetAddressRequestDto());
        customerRequestDto.setUserRequestDto(userRequestDto);
        return Instancio.of(OrderRequestDto.class)
                .set(field("customerRequestDto"), prepareCustomerRequestDto())
                .set(field("packageRequestDto"), preparePackageRequestDto())
                .create();
    }

    private CustomerRequestDto prepareCustomerRequestDto(){
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
        customerRequestDto.setId(1L);
        return customerRequestDto;
    }

    private UserRequestDto prepareUserRequestDto(){
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(1L);
        return userRequestDto;
    }

    private AddressRequestDto prepareAddressRequestDto(){
        AddressRequestDto addressRequestDto = new AddressRequestDto();
        addressRequestDto.setId(1L);
        return addressRequestDto;
    }

    private Set<AddressRequestDto> prepareSetAddressRequestDto(){
        return Set.of(prepareAddressRequestDto());
    }

    private PackageRequestDto preparePackageRequestDto() {
        PackageRequestDto packageRequestDto = new PackageRequestDto();
        packageRequestDto.setId(1L);
        return packageRequestDto;
    }

    private Customer prepareCustomer(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setScore(0);
        return customer;
    }

    private User prepareUser() {
        User user = new User();
        user.setId(1L);
        return user;
    }

    private CPackage prepareCPackage() {
        CPackage cPackage = new CPackage();
        cPackage.setId(1L);
        cPackage.setPrice(BigDecimal.valueOf(10000));
        cPackage.setPackageType(PackageType.PREMIUM);
        return cPackage;
    }

    private Order prepareOrder1(){
        Order order = new Order();
        order.setCreateDateTime(LocalDateTime.now());
        order.setCustomerId(1L);
        order.setPackageId(1L);
        order.setOrderCode(GenerateRandomUnique.createRandomOrderCode());
        order.setRecordStatus(RecordStatus.ACTIVE);
        order.setOrderStatus(OrderStatus.INITIAL);
        return order;
    }

    private Order prepareOrder2(){
        Order order = new Order();
        order.setCreateDateTime(LocalDateTime.now());
        order.setCustomerId(1L);
        order.setPackageId(1L);
        order.setOrderCode(GenerateRandomUnique.createRandomOrderCode());
        order.setRecordStatus(RecordStatus.ACTIVE);
        order.setOrderStatus(OrderStatus.INITIAL);
        return order;
    }

    private OrderSearchRequestDto prepareOrderSearchRequestDto() {
        OrderSearchRequestDto orderSearchRequestDto = new OrderSearchRequestDto();
        orderSearchRequestDto.setOrderCode("ORD1429327");
        orderSearchRequestDto.setOrderStatus(OrderStatus.INITIAL);
        orderSearchRequestDto.setCreateDate(LocalDate.now());
        orderSearchRequestDto.setPackageRequestDto(preparePackageRequestDto());
        orderSearchRequestDto.setCustomerRequestDto(prepareCustomerRequestDto());
        orderSearchRequestDto.setSort("DESC");
        orderSearchRequestDto.setPage(0);
        orderSearchRequestDto.setSize(1);
        return orderSearchRequestDto;
    }
}

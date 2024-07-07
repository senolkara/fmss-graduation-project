package com.senolkarakurt.orderservice.converter;

import com.senolkarakurt.dto.response.OrderResponseDto;
import com.senolkarakurt.enums.OrderStatus;
import com.senolkarakurt.orderservice.dto.request.OrderSaveRequestDto;
import com.senolkarakurt.orderservice.model.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

    public static Order toOrderByOrderSaveRequestDto(OrderSaveRequestDto orderSaveRequestDto){
        Order order = new Order();
        order.setCreateDateTime(LocalDateTime.now());
        order.setCustomerId(orderSaveRequestDto.getCustomer().getId());
        order.setOrderCode(orderSaveRequestDto.getOrderCode());
        order.setPackageId(orderSaveRequestDto.getACPackage().getId());
        order.setOrderStatus(OrderStatus.INITIAL);
        return order;
    }

    public static OrderResponseDto toOrderResponseDtoByOrder(Order order){
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setCreateDateTime(order.getCreateDateTime());
        orderResponseDto.setOrderCode(order.getOrderCode());
        orderResponseDto.setOrderStatus(order.getOrderStatus());
        return orderResponseDto;
    }

    public static List<OrderResponseDto> toResponse(List<Order> orderList){
        return orderList
                .stream()
                .map(OrderConverter::toOrderResponseDtoByOrder)
                .collect(Collectors.toList());
    }
}

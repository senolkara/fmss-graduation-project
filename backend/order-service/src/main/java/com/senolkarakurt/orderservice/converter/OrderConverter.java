package com.senolkarakurt.orderservice.converter;

import com.senolkarakurt.dto.response.OrderResponseDto;
import com.senolkarakurt.enums.OrderStatus;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.orderservice.dto.request.OrderSaveRequestDto;
import com.senolkarakurt.orderservice.model.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

    public static Order toOrderByOrderSaveRequestDto(OrderSaveRequestDto orderSaveRequestDto){
        Order order = new Order();
        order.setCreateDateTime(LocalDateTime.now());
        order.setCustomerId(orderSaveRequestDto.getCustomer().getId());
        order.setOrderCode(orderSaveRequestDto.getOrderCode());
        order.setPackageId(orderSaveRequestDto.getACPackage().getId());
        order.setOrderStatus(OrderStatus.INITIAL);
        order.setRecordStatus(RecordStatus.ACTIVE);
        return order;
    }

    public static OrderResponseDto toOrderResponseDtoByOrder(Order order){
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setRecordStatus(order.getRecordStatus());
        orderResponseDto.setCreateDateTime(order.getCreateDateTime());
        orderResponseDto.setOrderCode(order.getOrderCode());
        orderResponseDto.setOrderStatus(order.getOrderStatus());
        return orderResponseDto;
    }
}

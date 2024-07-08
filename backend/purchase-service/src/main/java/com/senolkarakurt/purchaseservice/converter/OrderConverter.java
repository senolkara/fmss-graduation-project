package com.senolkarakurt.purchaseservice.converter;

import com.senolkarakurt.dto.response.OrderResponseDto;
import com.senolkarakurt.purchaseservice.model.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

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

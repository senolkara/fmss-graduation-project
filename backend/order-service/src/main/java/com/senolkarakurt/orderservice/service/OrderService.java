package com.senolkarakurt.orderservice.service;

import com.senolkarakurt.dto.request.OrderRequestDto;
import com.senolkarakurt.dto.response.OrderResponseDto;
import com.senolkarakurt.orderservice.dto.request.OrderSearchRequestDto;
import com.senolkarakurt.orderservice.model.Order;

import java.util.List;

public interface OrderService {
    void save(OrderRequestDto orderRequestDto);
    List<OrderResponseDto> getAllByCustomerId(Long customerId);
    OrderResponseDto getById(Long id);
    Order getOrderById(Long id);
    List<OrderResponseDto> search(OrderSearchRequestDto orderSearchRequestDto);
}

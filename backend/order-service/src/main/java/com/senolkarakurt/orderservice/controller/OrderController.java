package com.senolkarakurt.orderservice.controller;

import com.senolkarakurt.dto.request.OrderRequestDto;
import com.senolkarakurt.dto.response.GenericResponse;
import com.senolkarakurt.dto.response.OrderResponseDto;
import com.senolkarakurt.exception.ExceptionSuccessCreatedMessage;
import com.senolkarakurt.orderservice.dto.request.OrderSearchRequestDto;
import com.senolkarakurt.orderservice.model.Order;
import com.senolkarakurt.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public GenericResponse<String> save(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.save(orderRequestDto);
        return GenericResponse.success(ExceptionSuccessCreatedMessage.ORDER_CREATED);
    }

    /**
     * Müşterinin bütün siparişlerini listele
     */
    @GetMapping("/customerId/{customerId}")
    public GenericResponse<List<OrderResponseDto>> getAllByCustomerId(@PathVariable("customerId") Long customerId) {
        return GenericResponse.success(orderService.getAllByCustomerId(customerId));
    }

    @GetMapping("/id/{id}")
    public Order getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/search")
    public GenericResponse<List<OrderResponseDto>> search(@RequestBody OrderSearchRequestDto orderSearchRequestDto){
        return GenericResponse.success(orderService.search(orderSearchRequestDto));
    }
}

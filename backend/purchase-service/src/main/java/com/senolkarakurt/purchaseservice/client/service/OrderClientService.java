package com.senolkarakurt.purchaseservice.client.service;

import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.purchaseservice.client.OrderClient;
import com.senolkarakurt.purchaseservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.purchaseservice.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderClientService {

    private final ExceptionMessagesResource exceptionMessagesResource;
    private final OrderClient orderClient;

    public Order getOrderById(Long id) {
        Order order = orderClient.getOrderById(id);
        if (order == null) {
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getOrderNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getOrderNotFoundWithId());
        }
        return order;
    }
}

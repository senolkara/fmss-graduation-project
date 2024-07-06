package com.senolkarakurt.purchaseservice.client;

import com.senolkarakurt.purchaseservice.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "order-service", url = "http://localhost:8097/api/v1/orders")
public interface OrderClient {

    @GetMapping("/id/{id}")
    Order getOrderById(@PathVariable("id") Long id);

}

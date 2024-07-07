package com.senolkarakurt.orderservice.client;

import com.senolkarakurt.orderservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.orderservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "customer-service", url = "http://localhost:8091/api/v1/customers")
public interface CustomerClient {

    @GetMapping("/id/{id}")
    Customer getCustomerById(@PathVariable("id") Long id);

    @PostMapping("/changeAccountTypeAndScore")
    void changeAccountTypeAndScore(CustomerUpdateRequestDto customerUpdateRequestDto);

}

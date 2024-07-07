package com.senolkarakurt.cpackageservice.client;

import com.senolkarakurt.cpackageservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "customer-service", url = "http://localhost:8091/api/v1/customers")
public interface CustomerClient {

    @GetMapping("/id/{id}")
    Customer getCustomerById(@PathVariable("id") Long id);

}

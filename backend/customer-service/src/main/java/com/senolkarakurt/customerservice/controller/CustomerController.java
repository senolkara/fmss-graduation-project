package com.senolkarakurt.customerservice.controller;

import com.senolkarakurt.customerservice.model.Customer;
import com.senolkarakurt.dto.request.CustomerRequestDto;
import com.senolkarakurt.dto.response.CustomerResponseDto;
import com.senolkarakurt.customerservice.service.CustomerService;
import com.senolkarakurt.customerservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.dto.response.GenericResponse;
import com.senolkarakurt.exception.ExceptionSuccessCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public GenericResponse<String> save(@RequestBody CustomerRequestDto customerRequestDto) {
        customerService.save(customerRequestDto);
        return GenericResponse.success(ExceptionSuccessCreatedMessage.CUSTOMER_CREATED);
    }

    @GetMapping("/id/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/changeAccountTypeAndScore")
    public void changeAccountTypeAndScore(@RequestBody CustomerUpdateRequestDto customerUpdateRequestDto){
        customerService.changeAccountTypeAndScore(customerUpdateRequestDto);
    }
}

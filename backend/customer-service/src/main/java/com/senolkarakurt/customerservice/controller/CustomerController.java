package com.senolkarakurt.customerservice.controller;

import com.senolkarakurt.customerservice.dto.response.RegistrationResponseDto;
import com.senolkarakurt.customerservice.model.Customer;
import com.senolkarakurt.dto.request.CustomerRequestDto;
import com.senolkarakurt.customerservice.service.CustomerService;
import com.senolkarakurt.customerservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.dto.request.RegistrationRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public RegistrationResponseDto save(@RequestBody RegistrationRequestDto registrationRequestDto) {
        return customerService.save(registrationRequestDto);
    }

    @GetMapping("/id/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/userId/{userId}")
    public Customer getCustomerByUserId(@PathVariable("userId") Long userId) {
        return customerService.getCustomerByUserId(userId);
    }

    @PutMapping("/changeAccountTypeAndScore/{id}")
    public void changeAccountTypeAndScore(@PathVariable("id") Long id, @RequestBody CustomerUpdateRequestDto customerUpdateRequestDto){
        customerService.changeAccountTypeAndScore(id, customerUpdateRequestDto);
    }
}

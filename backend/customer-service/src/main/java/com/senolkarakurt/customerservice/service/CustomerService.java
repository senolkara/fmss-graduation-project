package com.senolkarakurt.customerservice.service;

import com.senolkarakurt.customerservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.customerservice.model.Customer;
import com.senolkarakurt.dto.request.CustomerRequestDto;

public interface CustomerService {
    void save(CustomerRequestDto customerRequestDto);
    Customer getCustomerById(Long id);
    void changeAccountTypeAndScore(Long id, CustomerUpdateRequestDto customerUpdateRequestDto);
}

package com.senolkarakurt.customerservice.service;

import com.senolkarakurt.customerservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.customerservice.model.Customer;
import com.senolkarakurt.dto.request.CustomerRequestDto;
import com.senolkarakurt.dto.response.CustomerResponseDto;

import java.util.List;

public interface CustomerService {
    void save(CustomerRequestDto customerRequestDto);
    List<CustomerResponseDto> getAll();
    CustomerResponseDto getById(Long id);
    Customer getCustomerById(Long id);
    void changeAccountTypeAndScore(CustomerUpdateRequestDto customerUpdateRequestDto);
}

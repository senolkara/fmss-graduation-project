package com.senolkarakurt.customerservice.service;

import com.senolkarakurt.customerservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.customerservice.dto.response.RegistrationResponseDto;
import com.senolkarakurt.customerservice.model.Customer;
import com.senolkarakurt.dto.request.RegistrationRequestDto;

public interface CustomerService {
    RegistrationResponseDto save(RegistrationRequestDto registrationRequestDto);
    Customer getCustomerById(Long id);
    void changeAccountTypeAndScore(Long id, CustomerUpdateRequestDto customerUpdateRequestDto);
    Customer getCustomerByUserId(Long userId);
}

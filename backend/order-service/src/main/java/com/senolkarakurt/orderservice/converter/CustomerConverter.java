package com.senolkarakurt.orderservice.converter;

import com.senolkarakurt.orderservice.model.Customer;
import com.senolkarakurt.dto.response.CustomerResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerConverter {

    public static CustomerResponseDto toCustomerResponseDtoByCustomer(Customer customer){
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(customer.getId());
        customerResponseDto.setAccountType(customer.getAccountType());
        customerResponseDto.setScore(customer.getScore());
        return customerResponseDto;
    }

}

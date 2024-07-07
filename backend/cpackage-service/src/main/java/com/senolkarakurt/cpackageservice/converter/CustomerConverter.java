package com.senolkarakurt.cpackageservice.converter;

import com.senolkarakurt.dto.response.CustomerResponseDto;
import com.senolkarakurt.cpackageservice.model.Customer;
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

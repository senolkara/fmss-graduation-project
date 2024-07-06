package com.senolkarakurt.orderservice.dto.request;

import com.senolkarakurt.enums.AccountType;
import com.senolkarakurt.orderservice.model.Customer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerUpdateRequestDto {
    private Customer customer;
    private AccountType accountType;
    private Integer score;
}

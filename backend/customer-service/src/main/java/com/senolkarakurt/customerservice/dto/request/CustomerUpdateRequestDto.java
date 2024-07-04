package com.senolkarakurt.customerservice.dto.request;

import com.senolkarakurt.enums.AccountType;
import com.senolkarakurt.customerservice.model.Customer;
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

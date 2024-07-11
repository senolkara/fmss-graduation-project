package com.senolkarakurt.customerservice.dto.request;

import com.senolkarakurt.enums.AccountType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerUpdateRequestDto {
    private AccountType accountType;
    private Integer score;
}

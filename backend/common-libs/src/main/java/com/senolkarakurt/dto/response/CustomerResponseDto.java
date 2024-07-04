package com.senolkarakurt.dto.response;

import com.senolkarakurt.enums.AccountType;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerResponseDto implements Serializable {
    private Long id;
    private UserResponseDto userResponseDto;
    private AccountType accountType;
    private Integer score;
}

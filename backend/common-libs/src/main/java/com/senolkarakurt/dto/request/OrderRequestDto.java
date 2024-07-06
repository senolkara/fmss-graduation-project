package com.senolkarakurt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Long id;
    private PackageRequestDto packageRequestDto;
    private CustomerRequestDto customerRequestDto;
}

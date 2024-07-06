package com.senolkarakurt.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerPackageRequestDto {
    private Long id;
    private PackageRequestDto packageRequestDto;
    private CustomerRequestDto customerRequestDto;
}

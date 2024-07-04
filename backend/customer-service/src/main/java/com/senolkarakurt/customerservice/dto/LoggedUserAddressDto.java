package com.senolkarakurt.customerservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class LoggedUserAddressDto {
    private Long id;
    private String title;
    private String province;
    private String district;
    private String neighbourhood;
    private String street;
    private String description;
    private Boolean isActive;
}

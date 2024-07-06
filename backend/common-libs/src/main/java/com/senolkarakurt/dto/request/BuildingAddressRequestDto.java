package com.senolkarakurt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingAddressRequestDto {
    private Long id;
    private String title;

    @NotNull(message = "il alanını doldurunuz!")
    private String province;

    @NotNull(message = "ilçe alanını doldurunuz!")
    private String district;

    @NotNull(message = "mahalle alanını doldurunuz!")
    private String neighbourhood;

    @NotNull(message = "sokak alanını doldurunuz!")
    private String street;

    private String description;
}

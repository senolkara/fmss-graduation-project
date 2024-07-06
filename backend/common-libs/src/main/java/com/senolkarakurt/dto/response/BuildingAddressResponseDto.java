package com.senolkarakurt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingAddressResponseDto implements Serializable {
    private Long id;
    private String title;
    private String province;
    private String district;
    private String neighbourhood;
    private String street;
    private String description;
}

package com.senolkarakurt.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto implements Serializable {
    private Long id;
    private String title;
    private String province;
    private String district;
    private String neighbourhood;
    private String street;
    private String description;
}

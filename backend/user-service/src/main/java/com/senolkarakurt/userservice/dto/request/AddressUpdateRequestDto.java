package com.senolkarakurt.userservice.dto.request;

import com.senolkarakurt.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateRequestDto {
    private Long id;
    private String title;
    private String province;
    private String district;
    private String neighbourhood;
    private String street;
    private String description;
    private RecordStatus recordStatus;
}

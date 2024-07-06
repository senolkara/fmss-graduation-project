package com.senolkarakurt.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequestDto {
    private Long id;
    private UserRequestDto userRequestDto;
}

package com.senolkarakurt.customerservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerSaveRequestDto {
    private Long userId;
}

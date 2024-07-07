package com.senolkarakurt.orderservice.dto.request;

import com.senolkarakurt.orderservice.model.Customer;
import com.senolkarakurt.orderservice.model.CPackage;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderSaveRequestDto {
    private String orderCode;
    private CPackage aCPackage;
    private Customer customer;
}

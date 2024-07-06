package com.senolkarakurt.orderservice.dto.request;

import com.senolkarakurt.orderservice.model.Customer;
import com.senolkarakurt.orderservice.model.Package;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderSaveRequestDto {
    private String orderCode;
    private Package aPackage;
    private Customer customer;
}

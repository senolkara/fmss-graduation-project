package com.senolkarakurt.purchaseservice.producer.dto;

import com.senolkarakurt.dto.request.CustomerPackageRequestDto;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationCustomerPackageRequestDto implements Serializable {
    private CustomerPackageRequestDto customerPackageRequestDto;
}

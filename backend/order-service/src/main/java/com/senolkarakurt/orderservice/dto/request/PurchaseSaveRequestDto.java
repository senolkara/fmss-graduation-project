package com.senolkarakurt.orderservice.dto.request;

import com.senolkarakurt.orderservice.model.Purchase;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PurchaseSaveRequestDto {
    private Purchase purchase;
}

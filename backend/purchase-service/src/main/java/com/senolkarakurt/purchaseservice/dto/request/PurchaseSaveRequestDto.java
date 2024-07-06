package com.senolkarakurt.purchaseservice.dto.request;

import com.senolkarakurt.purchaseservice.model.Purchase;
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

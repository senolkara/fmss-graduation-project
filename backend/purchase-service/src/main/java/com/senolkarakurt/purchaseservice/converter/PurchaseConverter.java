package com.senolkarakurt.purchaseservice.converter;

import com.senolkarakurt.dto.response.PurchaseResponseDto;
import com.senolkarakurt.purchaseservice.model.Purchase;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseConverter {

    public static PurchaseResponseDto toPurchaseResponseDtoByPurchase(Purchase purchase) {
        PurchaseResponseDto purchaseResponseDto = new PurchaseResponseDto();
        purchaseResponseDto.setId(purchase.getId());
        purchaseResponseDto.setCreateDateTime(purchase.getCreateDateTime());
        return purchaseResponseDto;
    }
}

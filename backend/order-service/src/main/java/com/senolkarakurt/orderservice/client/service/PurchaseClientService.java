package com.senolkarakurt.orderservice.client.service;

import com.senolkarakurt.orderservice.client.PurchaseClient;
import com.senolkarakurt.orderservice.dto.request.PurchaseSaveRequestDto;
import com.senolkarakurt.orderservice.model.Purchase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PurchaseClientService {

    private final PurchaseClient purchaseClient;

    public void save(Purchase purchase){
        PurchaseSaveRequestDto purchaseSaveRequestDto = PurchaseSaveRequestDto.builder()
                .purchase(purchase)
                .build();
        purchaseClient.save(purchaseSaveRequestDto);
    }
}

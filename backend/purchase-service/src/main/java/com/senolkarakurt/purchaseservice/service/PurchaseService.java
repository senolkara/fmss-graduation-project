package com.senolkarakurt.purchaseservice.service;

import com.senolkarakurt.purchaseservice.dto.request.PurchaseSaveRequestDto;
import com.senolkarakurt.purchaseservice.model.Purchase;

public interface PurchaseService {
    void save(PurchaseSaveRequestDto purchaseSaveRequestDto);
    Purchase getPurchaseById(Long id);
}

package com.senolkarakurt.purchaseservice.service;

import com.senolkarakurt.dto.response.PurchaseResponseDto;
import com.senolkarakurt.purchaseservice.dto.request.PurchaseSaveRequestDto;
import com.senolkarakurt.purchaseservice.model.Purchase;

import java.util.List;

public interface PurchaseService {
    void save(PurchaseSaveRequestDto purchaseSaveRequestDto);
    List<PurchaseResponseDto> getAll();
    PurchaseResponseDto getById(Long id);
    Purchase getPurchaseById(Long id);
}

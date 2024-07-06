package com.senolkarakurt.purchaseservice.controller;

import com.senolkarakurt.purchaseservice.dto.request.PurchaseSaveRequestDto;
import com.senolkarakurt.purchaseservice.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/save")
    public void save(@RequestBody PurchaseSaveRequestDto purchaseSaveRequestDto){
        purchaseService.save(purchaseSaveRequestDto);
    }

}

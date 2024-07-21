package com.senolkarakurt.purchaseservice.controller;

import com.senolkarakurt.purchaseservice.dto.request.PurchaseSaveRequestDto;
import com.senolkarakurt.purchaseservice.model.Purchase;
import com.senolkarakurt.purchaseservice.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/purchases")
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/save")
    public void save(@RequestBody PurchaseSaveRequestDto purchaseSaveRequestDto){
        purchaseService.save(purchaseSaveRequestDto);
    }

    @GetMapping("/id/{id}")
    public Purchase getPurchaseById(@PathVariable("id") Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @GetMapping("/orderId/{orderId}")
    public Purchase getPurchaseByOrderId(@PathVariable("orderId") Long orderId) {
        return purchaseService.getPurchaseByOrderId(orderId);
    }
}

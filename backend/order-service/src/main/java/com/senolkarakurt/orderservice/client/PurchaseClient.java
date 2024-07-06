package com.senolkarakurt.orderservice.client;

import com.senolkarakurt.orderservice.dto.request.PurchaseSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "purchase-service", url = "http://localhost:8098/api/v1/purchases")
public interface PurchaseClient {

    @PostMapping("/save")
    void save(PurchaseSaveRequestDto purchaseSaveRequestDto);

}

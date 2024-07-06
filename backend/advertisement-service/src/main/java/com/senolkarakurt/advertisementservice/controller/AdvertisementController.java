package com.senolkarakurt.advertisementservice.controller;

import com.senolkarakurt.advertisementservice.model.Advertisement;
import com.senolkarakurt.advertisementservice.service.AdvertisementService;
import com.senolkarakurt.dto.request.AdvertisementRequestDto;
import com.senolkarakurt.dto.response.AdvertisementResponseDto;
import com.senolkarakurt.dto.response.GenericResponse;
import com.senolkarakurt.enums.AdvertisementStatus;
import com.senolkarakurt.exception.ExceptionSuccessCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    public GenericResponse<String> save(@RequestBody AdvertisementRequestDto advertisementRequestDto) {
        advertisementService.save(advertisementRequestDto);
        return GenericResponse.success(ExceptionSuccessCreatedMessage.ADVERTISEMENT_CREATED);
    }

    @GetMapping
    public GenericResponse<List<AdvertisementResponseDto>> getAll() {
        return GenericResponse.success(advertisementService.getAll());
    }

    @GetMapping("/{id}")
    public GenericResponse<AdvertisementResponseDto> getById(@PathVariable("id") Long id) {
        AdvertisementResponseDto advertisementResponseDto = advertisementService.getById(id);
        return GenericResponse.success(advertisementResponseDto);
    }

    @GetMapping("/id/{id}")
    public Advertisement getAdvertisementById(@PathVariable("id") Long id) {
        return advertisementService.getAdvertisementById(id);
    }

    @PutMapping("/changeAdvertisementStatus/{id}")
    public void changeAdvertisementStatus(@PathVariable("id") Long id, @RequestBody AdvertisementStatus advertisementStatus){
        advertisementService.changeAdvertisementStatus(id, advertisementStatus);
    }

}

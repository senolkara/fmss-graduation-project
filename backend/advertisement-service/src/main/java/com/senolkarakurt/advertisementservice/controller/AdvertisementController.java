package com.senolkarakurt.advertisementservice.controller;

import com.senolkarakurt.advertisementservice.dto.request.AdvertisementUpdateRequestDto;
import com.senolkarakurt.advertisementservice.model.Advertisement;
import com.senolkarakurt.advertisementservice.service.AdvertisementService;
import com.senolkarakurt.dto.request.AdvertisementRequestDto;
import com.senolkarakurt.dto.response.AdvertisementResponseDto;
import com.senolkarakurt.dto.response.BuildingResponseDto;
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
@CrossOrigin(origins = "http://localhost:3000")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    public GenericResponse<String> save(@RequestBody AdvertisementRequestDto advertisementRequestDto) {
        advertisementService.save(advertisementRequestDto);
        return GenericResponse.success(ExceptionSuccessCreatedMessage.ADVERTISEMENT_CREATED);
    }

    @PutMapping("/update/{id}")
    public GenericResponse<String> update(@PathVariable("id") Long id, @RequestBody AdvertisementUpdateRequestDto advertisementUpdateRequestDto) {
        advertisementService.update(id, advertisementUpdateRequestDto);
        return GenericResponse.success(ExceptionSuccessCreatedMessage.ADVERTISEMENT_UPDATED);
    }

    @DeleteMapping("/delete/{id}")
    public GenericResponse<String> delete(@PathVariable("id") Long id) {
        advertisementService.delete(id);
        return GenericResponse.success(ExceptionSuccessCreatedMessage.ADVERTISEMENT_DELETED);
    }

    @GetMapping("/customerId/{customerId}")
    public GenericResponse<List<AdvertisementResponseDto>> getAllByCustomerId(@PathVariable("customerId") Long customerId) {
        return GenericResponse.success(advertisementService.getAllByCustomerId(customerId));
    }

    @GetMapping("/customerId/{customerId}/filterByStatus")
    public GenericResponse<List<AdvertisementResponseDto>> getAllByCustomerIdFilterByStatus(@PathVariable("customerId") Long customerId,
                                                                              @RequestParam(value = "status") Integer status) {
        return GenericResponse.success(advertisementService.getAllByCustomerIdFilterByStatus(customerId, status));
    }

    @GetMapping("/id/{id}")
    public Advertisement getAdvertisementById(@PathVariable("id") Long id) {
        return advertisementService.getAdvertisementById(id);
    }

    @PutMapping("/changeAdvertisementStatus/{id}")
    public void changeAdvertisementStatus(@PathVariable("id") Long id, @RequestBody AdvertisementStatus advertisementStatus){
        advertisementService.changeAdvertisementStatus(id, advertisementStatus);
    }

    @GetMapping("/allBuildings")
    public GenericResponse<List<BuildingResponseDto>> getAllBuildings() {
        return GenericResponse.success(advertisementService.getAllBuildings());
    }

}

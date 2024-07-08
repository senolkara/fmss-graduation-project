package com.senolkarakurt.advertisementservice.service;

import com.senolkarakurt.advertisementservice.model.Advertisement;
import com.senolkarakurt.advertisementservice.model.Building;
import com.senolkarakurt.dto.request.AdvertisementRequestDto;
import com.senolkarakurt.dto.request.BuildingRequestDto;
import com.senolkarakurt.dto.response.AdvertisementResponseDto;
import com.senolkarakurt.enums.AdvertisementStatus;

import java.util.List;

public interface AdvertisementService {
    void save(AdvertisementRequestDto advertisementRequestDto);
    List<AdvertisementResponseDto> getAllByCustomerId(Long customerId);
    List<AdvertisementResponseDto> getAllByCustomerIdFilterByStatus(Long customerId, Integer status);
    AdvertisementResponseDto getById(Long id);
    Advertisement getAdvertisementById(Long id);
    Building getBuildingById(Long id);
    void changeAdvertisementStatus(Long id, AdvertisementStatus advertisementStatus);
}

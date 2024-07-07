package com.senolkarakurt.advertisementservice.component;

import com.senolkarakurt.advertisementservice.repository.BuildingRepository;
import com.senolkarakurt.advertisementservice.util.CreateFakeData;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FakeDataComponent {

    private final BuildingRepository buildingRepository;

    @PostConstruct
    public void init() {
        buildingRepository.saveAll(CreateFakeData.getHouseList());
        buildingRepository.saveAll(CreateFakeData.getVillaList());
        buildingRepository.saveAll(CreateFakeData.getSummerHouseList());
    }

}

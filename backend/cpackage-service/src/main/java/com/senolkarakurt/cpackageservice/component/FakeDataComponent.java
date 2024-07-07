package com.senolkarakurt.cpackageservice.component;

import com.senolkarakurt.cpackageservice.util.CreateFakeData;
import com.senolkarakurt.cpackageservice.repository.PackageRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FakeDataComponent {

    private final PackageRepository packageRepository;

    @PostConstruct
    public void init() {
        packageRepository.saveAll(CreateFakeData.getCPackageList());
    }

}

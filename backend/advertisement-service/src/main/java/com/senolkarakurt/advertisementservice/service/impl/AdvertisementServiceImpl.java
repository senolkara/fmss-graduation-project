package com.senolkarakurt.advertisementservice.service.impl;

import com.senolkarakurt.advertisementservice.client.service.CustomerClientService;
import com.senolkarakurt.advertisementservice.client.service.PackageClientService;
import com.senolkarakurt.advertisementservice.client.service.UserClientService;
import com.senolkarakurt.advertisementservice.converter.*;
import com.senolkarakurt.advertisementservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.advertisementservice.model.*;
import com.senolkarakurt.advertisementservice.model.Package;
import com.senolkarakurt.advertisementservice.producer.NotificationAdvertisementProducer;
import com.senolkarakurt.advertisementservice.producer.dto.NotificationAdvertisementDto;
import com.senolkarakurt.advertisementservice.repository.AdvertisementRepository;
import com.senolkarakurt.advertisementservice.repository.BuildingRepository;
import com.senolkarakurt.advertisementservice.service.AdvertisementService;
import com.senolkarakurt.dto.request.AdvertisementRequestDto;
import com.senolkarakurt.dto.response.*;
import com.senolkarakurt.enums.AdvertisementStatus;
import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.util.GenerateRandomUnique;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {

    private final BuildingRepository buildingRepository;
    private final AdvertisementRepository advertisementRepository;
    private final ExceptionMessagesResource exceptionMessagesResource;
    private final UserClientService userClientService;
    private final CustomerClientService customerClientService;
    private final PackageClientService packageClientService;
    private final NotificationAdvertisementProducer notificationAdvertisementProducer;

    @Override
    public void save(AdvertisementRequestDto advertisementRequestDto) {
        CustomerPackage customerPackage = packageClientService.getCustomerPackageById(advertisementRequestDto.getCustomerPackageRequestDto().getId());
        controlAdvertisementPackage(customerPackage);
        Building building = getBuildingById(advertisementRequestDto.getBuildingRequestDto().getId());
        Advertisement advertisement = Advertisement.builder()
                .advertisementStatus(AdvertisementStatus.IN_REVIEW)
                .advertisementNo(GenerateRandomUnique.createRandomAdvertisementNo())
                .startDateTime(LocalDateTime.now())
                .finishDateTime(LocalDateTime.now().plusDays(30))
                .buildingId(building.getId())
                .customerPackageId(customerPackage.getId())
                .build();
        advertisementRepository.save(advertisement);
        Integer advertisementCount = customerPackage.getAdvertisementCount() - 1;
        packageClientService.changeCustomerPackageAdvertisementCount(customerPackage.getId(), advertisementCount);

        NotificationAdvertisementDto notificationAdvertisementDto = NotificationAdvertisementDto.builder()
                .notificationType(NotificationType.EMAIL)
                .advertisementResponseDto(getAdvertisementResponseDto(advertisement))
                .build();
        notificationAdvertisementProducer.sendNotificationAdvertisement(notificationAdvertisementDto);
    }

    @Override
    public List<AdvertisementResponseDto> getAll() {
        List<AdvertisementResponseDto> advertisementResponseDtoList = new ArrayList<>();
        advertisementRepository.findAll().forEach(advertisement -> {
            advertisementResponseDtoList.add(getAdvertisementResponseDto(advertisement));
        });
        return advertisementResponseDtoList;
    }

    @Override
    public AdvertisementResponseDto getById(Long id) {
        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
        if (advertisementOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getAdvertisementNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getAdvertisementNotFoundWithId());
        }
        return getAdvertisementResponseDto(advertisementOptional.get());
    }

    @Override
    public Advertisement getAdvertisementById(Long id) {
        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
        return advertisementOptional.orElse(null);
    }

    @Override
    public Building getBuildingById(Long id){
        Optional<Building> buildingOptional = buildingRepository.findById(id);
        return buildingOptional.orElse(null);
    }

    @Override
    public void changeAdvertisementStatus(Long id, AdvertisementStatus advertisementStatus) {
        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
        if (advertisementOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getAdvertisementNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getAdvertisementNotFoundWithId());
        }
        Advertisement advertisement = advertisementOptional.get();
        advertisement.setAdvertisementStatus(advertisementStatus);
        advertisementRepository.save(advertisement);
    }

    private void controlAdvertisementPackage(CustomerPackage customerPackage){
        if (customerPackage.getAdvertisementCount() < 0){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPackageAdvertisementCountZero(), customerPackage.getId()));
            throw new CommonException(exceptionMessagesResource.getPackageAdvertisementCountZero());
        }
        if (customerPackage.getFinishDateTime().isBefore(LocalDateTime.now())){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPackageValidDateTimeFinish(), customerPackage.getId()));
            throw new CommonException(exceptionMessagesResource.getPackageValidDateTimeFinish());
        }
    }

    private AdvertisementResponseDto getAdvertisementResponseDto(Advertisement advertisement){
        AdvertisementResponseDto advertisementResponseDto = AdvertisementConverter.toAdvertisementResponseDtoByAdvertisement(advertisement);
        CustomerPackage customerPackage = packageClientService.getCustomerPackageById(advertisement.getCustomerPackageId());
        Package packageById = packageClientService.getPackageById(customerPackage.getPackageId());
        PackageResponseDto packageResponseDto = PackageConverter.toPackageResponseDtoByPackage(packageById);
        CustomerPackageResponseDto customerPackageResponseDto = PackageConverter.toCustomerPackageResponseDtoByCustomerPackage(customerPackage);
        customerPackageResponseDto.setPackageResponseDto(packageResponseDto);
        Customer customer = customerClientService.getCustomerById(customerPackage.getCustomerId());
        CustomerResponseDto customerResponseDto = CustomerConverter.toCustomerResponseDtoByCustomer(customer);
        User user = userClientService.getUserById(customer.getUserId());
        UserResponseDto userResponseDto = UserConverter.toUserResponseDtoByUser(user);
        Set<Address> addresses = userClientService.getAddressesByUserId(user.getId());
        Set<AddressResponseDto> addressResponseDtoSet = AddressConverter.toSetAddressesResponseDtoBySetAddresses(addresses);
        userResponseDto.setAddressResponseDtoSet(addressResponseDtoSet);
        customerResponseDto.setUserResponseDto(userResponseDto);
        Building building = getBuildingById(advertisement.getBuildingId());
        BuildingResponseDto buildingResponseDto = BuildingConverter.toBuildingResponseDtoByBuilding(building);
        if (building instanceof House house){
            HouseResponseDto houseResponseDto = HouseConverter.toHouseResponseDtoByHouse(house);
            buildingResponseDto.setHouseResponseDto(houseResponseDto);
        }
        advertisementResponseDto.setBuildingResponseDto(buildingResponseDto);
        customerPackageResponseDto.setCustomerResponseDto(customerResponseDto);
        advertisementResponseDto.setCustomerPackageResponseDto(customerPackageResponseDto);
        return advertisementResponseDto;
    }

}

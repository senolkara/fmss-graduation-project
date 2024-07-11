package com.senolkarakurt.advertisementservice.service.impl;

import com.senolkarakurt.advertisementservice.client.service.CustomerClientService;
import com.senolkarakurt.advertisementservice.client.service.PackageClientService;
import com.senolkarakurt.advertisementservice.client.service.UserClientService;
import com.senolkarakurt.advertisementservice.converter.*;
import com.senolkarakurt.advertisementservice.dto.request.AdvertisementUpdateRequestDto;
import com.senolkarakurt.advertisementservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.advertisementservice.model.*;
import com.senolkarakurt.advertisementservice.model.CPackage;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (customerPackage.getId() == null){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFoundWithId(), advertisementRequestDto.getCustomerPackageRequestDto().getId()));
            throw new CommonException(exceptionMessagesResource.getPackageNotFoundWithId());
        }
        controlAdvertisementPackage(customerPackage);
        Building building = getBuildingById(advertisementRequestDto.getBuildingRequestDto().getId());
        controlBuilding(building);
        Advertisement advertisement = AdvertisementConverter.toAdvertisementByAdvertisementRequestDto(advertisementRequestDto);
        advertisement.setBuildingId(building.getId());
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
    public List<AdvertisementResponseDto> getAllByCustomerId(Long customerId) {
        List<AdvertisementResponseDto> advertisementResponseDtoList = new ArrayList<>();
        List<Advertisement> advertisementList = advertisementRepository.findAll()
                .stream()
                .filter(advertisement -> packageClientService.getCustomerPackageById(advertisement.getCustomerPackageId()).getCustomerId().equals(customerId))
                .toList();
        advertisementList.forEach(advertisement -> advertisementResponseDtoList.add(getAdvertisementResponseDto(advertisement)));
        return advertisementResponseDtoList;
    }

    @Override
    public List<AdvertisementResponseDto> getAllByCustomerIdFilterByStatus(Long customerId, Integer status) {
        List<AdvertisementResponseDto> advertisementResponseDtoList = getAllByCustomerId(customerId);
        if (status != null){
            AdvertisementStatus advertisementStatus = AdvertisementStatus.fromValue(status);
            if (advertisementStatus == null){
                return advertisementResponseDtoList;
            }
            return getAllByCustomerId(customerId)
                    .stream()
                    .filter(advertisementResponseDto -> advertisementResponseDto.getAdvertisementStatus().equals(advertisementStatus))
                    .collect(Collectors.toList());
        }
        return advertisementResponseDtoList;
    }

    @Override
    public Advertisement getAdvertisementById(Long id) {
        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
        if (advertisementOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getAdvertisementNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getAdvertisementNotFoundWithId());
        }
        return advertisementOptional.orElse(null);
    }

    @Override
    public Building getBuildingById(Long id){
        Optional<Building> buildingOptional = buildingRepository.findById(id);
        return buildingOptional.orElse(null);
    }

    private void controlBuilding(Building building){
        if (building == null ||
                !building.getRecordStatus().equals(RecordStatus.ACTIVE)){
            log.error("%s : {}".formatted(exceptionMessagesResource.getBuildingNotFound()));
            throw new CommonException(exceptionMessagesResource.getBuildingNotFound());
        }
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

    @Override
    public void update(Long id, AdvertisementUpdateRequestDto advertisementUpdateRequestDto) {
        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
        if (advertisementOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getAdvertisementNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getAdvertisementNotFoundWithId());
        }
        Advertisement advertisement = advertisementOptional.get();
        advertisement.setAdvertisementType(advertisementUpdateRequestDto.getAdvertisementType());
        advertisement.setPrice(advertisementUpdateRequestDto.getPrice());
        advertisementRepository.save(advertisement);
    }

    @Override
    public void delete(Long id) {
        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
        if (advertisementOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getAdvertisementNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getAdvertisementNotFoundWithId());
        }
        Advertisement advertisement = advertisementOptional.get();
        advertisementRepository.delete(advertisement);
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
        CPackage CPackageById = packageClientService.getPackageById(customerPackage.getPackageId());
        PackageResponseDto packageResponseDto = PackageConverter.toPackageResponseDtoByPackage(CPackageById);
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
        if (building instanceof SummerHouse summerHouse){
            SummerHouseResponseDto summerHouseResponseDto = SummerHouseConverter.toSummerHouseResponseDtoBySummerHouse(summerHouse);
            buildingResponseDto.setSummerHouseResponseDto(summerHouseResponseDto);
        }
        if (building instanceof Villa villa){
            VillaResponseDto villaResponseDto = VillaConverter.toVillaResponseDtoByVilla(villa);
            buildingResponseDto.setVillaResponseDto(villaResponseDto);
        }
        advertisementResponseDto.setBuildingResponseDto(buildingResponseDto);
        customerPackageResponseDto.setCustomerResponseDto(customerResponseDto);
        advertisementResponseDto.setCustomerPackageResponseDto(customerPackageResponseDto);
        return advertisementResponseDto;
    }

}

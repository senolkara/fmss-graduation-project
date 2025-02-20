package com.senolkarakurt.cpackageservice.service.impl;

import com.senolkarakurt.cpackageservice.model.CPackage;
import com.senolkarakurt.cpackageservice.service.SystemLogService;
import com.senolkarakurt.dto.request.CustomerPackageRequestDto;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.dto.response.*;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.cpackageservice.client.service.CustomerClientService;
import com.senolkarakurt.cpackageservice.client.service.UserClientService;
import com.senolkarakurt.cpackageservice.converter.AddressConverter;
import com.senolkarakurt.cpackageservice.converter.CustomerConverter;
import com.senolkarakurt.cpackageservice.converter.PackageConverter;
import com.senolkarakurt.cpackageservice.converter.UserConverter;
import com.senolkarakurt.cpackageservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.cpackageservice.model.*;
import com.senolkarakurt.cpackageservice.repository.CustomerPackageRepository;
import com.senolkarakurt.cpackageservice.repository.PackageRepository;
import com.senolkarakurt.cpackageservice.service.PackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;
    private final CustomerPackageRepository customerPackageRepository;
    private final ExceptionMessagesResource exceptionMessagesResource;
    private final UserClientService userClientService;
    private final CustomerClientService customerClientService;
    private final SystemLogService systemLogService;

    public PackageServiceImpl(PackageRepository packageRepository,
                              CustomerPackageRepository customerPackageRepository,
                              ExceptionMessagesResource exceptionMessagesResource,
                              UserClientService userClientService,
                              CustomerClientService customerClientService,
                              @Qualifier("dbLogService") SystemLogService systemLogService) {
        this.packageRepository = packageRepository;
        this.customerPackageRepository = customerPackageRepository;
        this.exceptionMessagesResource = exceptionMessagesResource;
        this.userClientService = userClientService;
        this.customerClientService = customerClientService;
        this.systemLogService = systemLogService;
    }

    @Override
    public void save(CustomerPackageRequestDto customerPackageRequestDto) {
        CustomerPackage customerPackage;
        LocalDateTime startDateTime;
        LocalDateTime finishDateTime;
        Integer advertisementCount = 10;
        Customer customer = customerClientService.getCustomerById(customerPackageRequestDto.getCustomerRequestDto().getId());
        List<CustomerPackage> customerPackageList = customerPackageRepository.findAllByCustomerIdAndFinishDateTimeGreaterThanAndRecordStatusOrderByFinishDateTimeDesc(customer.getId(), LocalDateTime.now(), RecordStatus.ACTIVE);
        if (CollectionUtils.isEmpty(customerPackageList)){
            startDateTime = LocalDateTime.now();
            finishDateTime = LocalDateTime.now().plusDays(30);
            customerPackage = CustomerPackage.builder()
                    .packageId(customerPackageRequestDto.getPackageRequestDto().getId())
                    .recordStatus(RecordStatus.ACTIVE)
                    .startDateTime(startDateTime)
                    .finishDateTime(finishDateTime)
                    .advertisementCount(advertisementCount)
                    .customerId(customer.getId())
                    .build();
        }else{
            customerPackage = customerPackageList.getLast();
            startDateTime = customerPackage.getStartDateTime();
            finishDateTime = customerPackage.getFinishDateTime().plusDays(30);
            advertisementCount += customerPackage.getAdvertisementCount();
            customerPackage.setStartDateTime(startDateTime);
            customerPackage.setFinishDateTime(finishDateTime);
            customerPackage.setAdvertisementCount(advertisementCount);
            customerPackage.setPackageId(customerPackageRequestDto.getPackageRequestDto().getId());
        }
        customerPackageRepository.save(customerPackage);
    }

    @Override
    public List<CustomerPackageResponseDto> getAllByCustomerId(Long customerId) {
        Customer customer = customerClientService.getCustomerById(customerId);
        List<CustomerPackageResponseDto> customerPackageResponseDtoList = new ArrayList<>();
        customerPackageRepository.findAllByCustomerId(customer.getId()).forEach(customerPackage -> {
            customerPackageResponseDtoList.add(getCustomerPackageResponseDto(customerPackage));
        });
        return customerPackageResponseDtoList;
    }

    @Override
    public CPackage getPackageById(Long id) {
        Optional<CPackage> packageOptional = packageRepository.findById(id);
        if (packageOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFoundWithId(), id));
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getPackageNotFoundWithId());
        }
        return packageOptional.orElse(null);
    }

    @Override
    public CustomerPackage getCustomerPackageById(Long id) {
        Optional<CustomerPackage> customerPackageOptional = customerPackageRepository.findByIdAndRecordStatus(id, RecordStatus.ACTIVE);
        if (customerPackageOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFoundWithId(), id));
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getPackageNotFoundWithId());
        }
        return customerPackageOptional.orElse(null);
    }

    @Override
    public void changeCustomerPackageAdvertisementCount(Long id, Integer advertisementCount) {
        Optional<CustomerPackage> customerPackageOptional = customerPackageRepository.findByIdAndFinishDateTimeGreaterThanAndRecordStatus(id, LocalDateTime.now(), RecordStatus.ACTIVE);
        if (customerPackageOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFoundWithId(), id));
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getPackageNotFoundWithId());
        }
        CustomerPackage customerPackage = customerPackageOptional.get();
        if (advertisementCount >= 0){
            customerPackage.setAdvertisementCount(advertisementCount);
            customerPackageRepository.save(customerPackage);
        }
    }

    @Override
    public List<CPackage> getAllPackages() {
        return packageRepository.findAll();
    }

    private void saveSystemLog(String content){
        SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                .recordDateTime(LocalDateTime.now())
                .content(content)
                .build();
        systemLogService.save(systemLogSaveRequestDto);
    }

    private CustomerPackageResponseDto getCustomerPackageResponseDto(CustomerPackage customerPackage){
        Optional<CPackage> packageOptional = packageRepository.findById(customerPackage.getPackageId());
        if (packageOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFoundWithId(), customerPackage.getPackageId()));
            throw new CommonException(exceptionMessagesResource.getPackageNotFoundWithId());
        }
        PackageResponseDto packageResponseDto = PackageConverter.toPackageResponseDtoByPackage(packageOptional.get());
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
        customerPackageResponseDto.setCustomerResponseDto(customerResponseDto);
        return customerPackageResponseDto;
    }
}

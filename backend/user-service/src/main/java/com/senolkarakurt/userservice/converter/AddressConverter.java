package com.senolkarakurt.userservice.converter;

import com.senolkarakurt.dto.request.AddressRequestDto;
import com.senolkarakurt.dto.response.AddressResponseDto;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressConverter {

    public static Set<Address> toSetAddressBySetAddressRequestDto(Set<AddressRequestDto> addressRequestDtoSet, User user){
        Set<Address> addresses = new HashSet<>();
        addressRequestDtoSet.forEach(addressRequestDto -> {
            Address address = new Address();
            address.setRecordStatus(RecordStatus.ACTIVE);
            address.setTitle(addressRequestDto.getTitle());
            address.setProvince(addressRequestDto.getProvince());
            address.setDistrict(addressRequestDto.getDistrict());
            address.setNeighbourhood(addressRequestDto.getNeighbourhood());
            address.setStreet(addressRequestDto.getStreet());
            address.setDescription(addressRequestDto.getDescription());
            address.setUserId(user.getId());
            addresses.add(address);
        });
        return addresses;
    }

}

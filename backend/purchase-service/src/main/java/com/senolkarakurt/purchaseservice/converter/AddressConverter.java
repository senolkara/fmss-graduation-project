package com.senolkarakurt.purchaseservice.converter;

import com.senolkarakurt.dto.request.AddressRequestDto;
import com.senolkarakurt.dto.response.AddressResponseDto;
import com.senolkarakurt.purchaseservice.model.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressConverter {

    public static Set<AddressResponseDto> toSetAddressesResponseDtoBySetAddresses(Set<Address> addresses){
        Set<AddressResponseDto> addressResponseDtoSet = new HashSet<>();
        addresses.forEach(address -> {
            AddressResponseDto addressResponseDto = toAddressResponseDtoByAddress(address);
            addressResponseDtoSet.add(addressResponseDto);
        });
        return addressResponseDtoSet;
    }

    public static AddressResponseDto toAddressResponseDtoByAddress(Address address){
        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setId(address.getId());
        addressResponseDto.setTitle(address.getTitle());
        addressResponseDto.setProvince(address.getProvince());
        addressResponseDto.setDistrict(address.getDistrict());
        addressResponseDto.setNeighbourhood(address.getNeighbourhood());
        addressResponseDto.setStreet(address.getStreet());
        addressResponseDto.setDescription(address.getDescription());
        return addressResponseDto;
    }

    public static AddressRequestDto toAddressRequestDtoByAddress(Address address){
        AddressRequestDto addressRequestDto = new AddressRequestDto();
        addressRequestDto.setId(address.getId());
        addressRequestDto.setTitle(address.getTitle());
        addressRequestDto.setProvince(address.getProvince());
        addressRequestDto.setDistrict(address.getDistrict());
        addressRequestDto.setNeighbourhood(address.getNeighbourhood());
        addressRequestDto.setStreet(address.getStreet());
        addressRequestDto.setDescription(address.getDescription());
        return addressRequestDto;
    }
}

package com.senolkarakurt.userservice.converter;

import com.senolkarakurt.dto.request.AddressRequestDto;
import com.senolkarakurt.dto.response.AddressResponseDto;
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

    public static Set<AddressResponseDto> toSetAddressesResponseDtoBySetAddresses(Set<Address> addresses){
        Set<AddressResponseDto> addressResponseDtoSet = new HashSet<>();
        if (addresses != null){
            addresses.forEach(address -> {
                AddressResponseDto addressResponseDto = toAddressResponseDtoByAddress(address);
                addressResponseDtoSet.add(addressResponseDto);
            });
        }
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
}

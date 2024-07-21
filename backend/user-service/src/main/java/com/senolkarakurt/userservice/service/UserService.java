package com.senolkarakurt.userservice.service;

import com.senolkarakurt.userservice.dto.request.AddressUpdateRequestDto;
import com.senolkarakurt.userservice.dto.request.UserUpdateRequestDto;
import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User getUserById(Long id);
    Set<Address> getAddressesByUserId(Long userId);
    List<Address> getAddressListByUserId(Long userId);
    String getValidUserToken();
    void update(Long userId, UserUpdateRequestDto userUpdateRequestDto);
    void updateAddress(Long userId, List<AddressUpdateRequestDto> addressUpdateRequestDtoList);
}

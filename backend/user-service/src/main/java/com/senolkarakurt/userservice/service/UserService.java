package com.senolkarakurt.userservice.service;

import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.dto.response.UserResponseDto;
import com.senolkarakurt.userservice.dto.request.LoginRequestDto;
import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User save(UserRequestDto userRequestDto);
    User getUserById(Long id);
    Set<Address> getAddressesByUserId(Long userId);
    void login(LoginRequestDto loginRequestDto);
}

package com.senolkarakurt.userservice.service;

import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.dto.response.UserResponseDto;
import com.senolkarakurt.userservice.dto.request.AuthenticationRequestDto;
import com.senolkarakurt.userservice.dto.response.AuthenticationResponseDto;
import com.senolkarakurt.userservice.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    User register(UserRequestDto userRequestDto);
    UserResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
    void saveUserToken(User user, String jwtToken);
    void revokeAllUserTokens(User user);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}

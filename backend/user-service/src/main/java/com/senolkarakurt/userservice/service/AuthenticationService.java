package com.senolkarakurt.userservice.service;

import com.senolkarakurt.dto.request.RegistrationRequestDto;
import com.senolkarakurt.userservice.dto.request.AuthenticationRequestDto;
import com.senolkarakurt.userservice.dto.response.AuthenticationResponseDto;
import com.senolkarakurt.userservice.dto.response.RegistrationResponseDto;
import com.senolkarakurt.userservice.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto);
    AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
    void saveUserToken(User user, String jwtToken);
    void revokeAllUserTokens(User user);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}

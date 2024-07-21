package com.senolkarakurt.userservice.controller;

import com.senolkarakurt.dto.request.RegistrationRequestDto;
import com.senolkarakurt.userservice.dto.request.AuthenticationRequestDto;
import com.senolkarakurt.userservice.dto.response.AuthenticationResponseDto;
import com.senolkarakurt.userservice.dto.response.RegistrationResponseDto;
import com.senolkarakurt.userservice.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public RegistrationResponseDto register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        return authenticationService.register(registrationRequestDto);
    }
    @PostMapping("/authenticate")
    public AuthenticationResponseDto authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        return authenticationService.authenticate(authenticationRequestDto);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

}

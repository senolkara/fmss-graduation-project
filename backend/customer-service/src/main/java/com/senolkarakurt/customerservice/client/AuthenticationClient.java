package com.senolkarakurt.customerservice.client;

import com.senolkarakurt.customerservice.dto.response.RegistrationResponseDto;
import com.senolkarakurt.dto.request.UserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "user-service", url = "http://localhost:8090/api/v1/auth")
public interface AuthenticationClient {

    @PostMapping("/register")
    RegistrationResponseDto register(UserRequestDto userRequestDto);

}

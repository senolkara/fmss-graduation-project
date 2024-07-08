package com.senolkarakurt.userservice.controller;

import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.dto.response.GenericResponse;
import com.senolkarakurt.dto.response.UserResponseDto;
import com.senolkarakurt.exception.ExceptionSuccessCreatedMessage;
import com.senolkarakurt.userservice.dto.request.LoginRequestDto;
import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;
import com.senolkarakurt.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping({"","/","/save"})
    public User save(@RequestBody UserRequestDto userRequestDto) {
        return userService.save(userRequestDto);
    }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/userId/{userId}")
    public Set<Address> getAddressesByUserId(@PathVariable("userId") Long userId){
        return userService.getAddressesByUserId(userId);
    }

    @PostMapping("/login")
    public GenericResponse<String> login(@RequestBody LoginRequestDto loginRequestDto){
        userService.login(loginRequestDto);
        return GenericResponse.success(ExceptionSuccessCreatedMessage.LOGIN_SUCCESS);
    }

}

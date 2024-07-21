package com.senolkarakurt.userservice.controller;

import com.senolkarakurt.userservice.dto.request.UserUpdateRequestDto;
import com.senolkarakurt.dto.response.GenericResponse;
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
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/addresses/userId/{userId}")
    public Set<Address> getAddressesByUserId(@PathVariable("userId") Long userId){
        return userService.getAddressesByUserId(userId);
    }

    @GetMapping("/addressList/userId/{userId}")
    public GenericResponse<List<Address>> getAddressListByUserId(@PathVariable("userId") Long userId){
        return GenericResponse.success(userService.getAddressListByUserId(userId));
    }

    @PutMapping("/update/{userId}")
    public void update(@PathVariable("userId") Long userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto){
        userService.update(userId, userUpdateRequestDto);
    }

    @GetMapping("/validUserToken")
    public String getValidUserToken(){
        return userService.getValidUserToken();
    }

}

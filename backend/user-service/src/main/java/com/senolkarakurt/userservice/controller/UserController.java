package com.senolkarakurt.userservice.controller;

import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;
import com.senolkarakurt.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/userId/{userId}")
    public Set<Address> getAddressesByUserId(@PathVariable("userId") Long userId){
        return userService.getAddressesByUserId(userId);
    }

}

package com.senolkarakurt.userservice.service;

import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;

import java.util.Set;

public interface UserService {
    User getUserById(Long id);
    Set<Address> getAddressesByUserId(Long userId);
}

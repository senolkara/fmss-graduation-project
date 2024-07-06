package com.senolkarakurt.orderservice.client;

import com.senolkarakurt.orderservice.model.Address;
import com.senolkarakurt.orderservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(value = "user-service", url = "http://localhost:8090/api/v1/users")
public interface UserClient {

    @GetMapping("/id/{id}")
    User getUserById(@PathVariable("id") Long id);

    @GetMapping("/userId/{userId}")
    Set<Address> getAddressesByUserId(@PathVariable("userId") Long userId);

}

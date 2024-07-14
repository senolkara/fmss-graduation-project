package com.senolkarakurt.userservice.dto.response;

import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDto implements Serializable {
    private User user;
    private Set<Address> addresses;
}

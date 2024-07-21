package com.senolkarakurt.customerservice.dto.response;

import com.senolkarakurt.customerservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDto implements Serializable {
    private User user;
}

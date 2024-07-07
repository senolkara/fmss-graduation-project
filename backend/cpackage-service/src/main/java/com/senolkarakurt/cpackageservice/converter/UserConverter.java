package com.senolkarakurt.cpackageservice.converter;

import com.senolkarakurt.dto.response.UserResponseDto;
import com.senolkarakurt.cpackageservice.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {

    public static UserResponseDto toUserResponseDtoByUser(User user){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setSurname(user.getSurname());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());
        userResponseDto.setActive(user.isActive());
        userResponseDto.setBirthDate(user.getBirthDate());
        return userResponseDto;
    }

}

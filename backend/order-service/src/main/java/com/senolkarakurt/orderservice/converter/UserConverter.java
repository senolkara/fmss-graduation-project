package com.senolkarakurt.orderservice.converter;

import com.senolkarakurt.orderservice.model.User;
import com.senolkarakurt.dto.response.UserResponseDto;
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
        userResponseDto.setRecordStatus(user.getRecordStatus());
        userResponseDto.setBirthDate(user.getBirthDate());
        userResponseDto.setRoleType(user.getRoleType());
        return userResponseDto;
    }

}

package com.senolkarakurt.userservice.converter;

import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.dto.response.UserResponseDto;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.enums.RoleType;
import com.senolkarakurt.userservice.model.User;
import com.senolkarakurt.util.GenerateRandomUnique;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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

    public static User toUserByUserRequestDto(UserRequestDto userRequestDto){
        return User.builder()
                .name(userRequestDto.getName())
                .surname(userRequestDto.getSurname())
                .email(userRequestDto.getEmail())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .recordStatus(RecordStatus.ACTIVE)
                .birthDate(userRequestDto.getBirthDate())
                .roleType(RoleType.USER)
                .build();
    }

    public static List<UserResponseDto> toResponse(List<User> userList) {
        return userList
                .stream()
                .map(UserConverter::toUserResponseDtoByUser)
                .collect(Collectors.toList());
    }
}

package com.senolkarakurt.userservice.converter;

import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.dto.response.UserResponseDto;
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
        userResponseDto.setActive(user.isActive());
        userResponseDto.setBirthDate(user.getBirthDate());
        return userResponseDto;
    }

    public static User toUserByUserRequestDto(UserRequestDto userRequestDto){
        String password = GenerateRandomUnique.createRandomHash(userRequestDto.getPassword());
        return new User(
                userRequestDto.getName(),
                userRequestDto.getSurname(),
                userRequestDto.getEmail(),
                password,
                userRequestDto.getPhoneNumber(),
                userRequestDto.getBirthDate()
        );
    }

    public static List<UserResponseDto> toResponse(List<User> userList) {
        return userList
                .stream()
                .map(UserConverter::toUserResponseDtoByUser)
                .collect(Collectors.toList());
    }
}

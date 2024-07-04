package com.senolkarakurt.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class LoggedUserDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private Boolean isActive;
    private Set<LoggedUserAddressDto> addressDtoSet;
    private LocalDate birthDate;
    private LocalDateTime localDateTime;
}

package com.senolkarakurt.userservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {

    @Size(min = 2, max = 255)
    private String name;

    @Size(min = 2, max = 255)
    private String surname;

    @NotEmpty(message = "email alanını doldurunuz!")
    @Email(message = "email formatı uygun değil")
    private String email;

    private String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private List<AddressUpdateRequestDto> addressUpdateRequestDtoList;

}

package com.senolkarakurt.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private Long id;

    @NotEmpty(message = "ad alanını doldurunuz!")
    @Size(min = 2, max = 255)
    private String name;

    @NotEmpty(message = "soyad alanını doldurunuz!")
    @Size(min = 2, max = 255)
    private String surname;

    @NotEmpty(message = "email alanını doldurunuz!")
    @Email(message = "email formatı uygun değil")
    private String email;

    @NotEmpty(message = "parola alanını doldurunuz!")
    @Size(min = 8, max = 20)
    private String password;

    private String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private Set<AddressRequestDto> addressRequestDtoSet;
}

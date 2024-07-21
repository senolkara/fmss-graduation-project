package com.senolkarakurt.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequestDto {

    @Size(min = 2, max = 255)
    private String name;

    @Size(min = 2, max = 255)
    private String surname;

    @NotEmpty(message = "email alanını doldurunuz!")
    @Email(message = "email formatı uygun değil")
    private String email;

    @NotEmpty(message = "parola alanını doldurunuz!")
    @Size(min = 8, max = 20)
    private String password;

}

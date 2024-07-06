package com.senolkarakurt.orderservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @NotEmpty(message = "email alanını doldurunuz!")
    @Email(message = "email formatı uygun değil")
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty(message = "parola alanını doldurunuz!")
    @Size(min = 8, max = 20)
    @Column(name = "password", nullable = false)
    private String password;

    @Size(min = 10, max = 11)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_active")
    private boolean isActive;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    public User(String name, String surname, String email, String password, String phoneNumber, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isActive = true;
        this.birthDate = birthDate;
    }
}

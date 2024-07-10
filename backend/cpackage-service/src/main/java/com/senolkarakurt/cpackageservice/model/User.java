package com.senolkarakurt.cpackageservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "record_status", nullable = false)
    private RecordStatus recordStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role_type", nullable = false)
    private RoleType roleType;

}

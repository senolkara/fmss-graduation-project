package com.senolkarakurt.userservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roleType.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

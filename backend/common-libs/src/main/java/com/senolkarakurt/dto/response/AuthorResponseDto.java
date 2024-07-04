package com.senolkarakurt.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorResponseDto implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private String bio;
}

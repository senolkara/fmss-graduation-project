package com.senolkarakurt.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.enums.RoleType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDto implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private RoleType roleType;
    private RecordStatus recordStatus;

    @JsonProperty("birthDate")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    private Set<AddressResponseDto> addressResponseDtoSet;
}

package com.senolkarakurt.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherRequestDto {

    private Long id;

    @NotEmpty(message = "yayınevi alanını doldurunuz!")
    @Size(min = 2, max = 255)
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
}

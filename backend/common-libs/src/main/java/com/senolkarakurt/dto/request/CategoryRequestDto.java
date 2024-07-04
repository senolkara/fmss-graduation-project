package com.senolkarakurt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {

    private Long id;

    @NotEmpty(message = "kategori adı alanını doldurunuz!")
    @Size(min = 2, max = 255)
    private String name;

    private String description;
}

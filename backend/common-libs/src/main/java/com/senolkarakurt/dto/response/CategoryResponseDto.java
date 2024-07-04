package com.senolkarakurt.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryResponseDto implements Serializable {
    private Long id;
    private String name;
    private String description;
}

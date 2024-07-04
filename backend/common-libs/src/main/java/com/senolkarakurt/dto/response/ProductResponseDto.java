package com.senolkarakurt.dto.response;

import com.senolkarakurt.enums.ProductType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponseDto implements Serializable {
    private Long id;
    private String name;
    private BigDecimal amount;
    private String description;
    private PublisherResponseDto publisherResponseDto;
    private AuthorResponseDto authorResponseDto;
    private ProductType productType;
    private CategoryResponseDto categoryResponseDto;
    private Integer stock;
}

package com.senolkarakurt.dto.request;

import com.senolkarakurt.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    private Long id;

    @NotEmpty(message = "ürün adı alanını doldurunuz!")
    @Size(min = 2, max = 255)
    private String name;

    @NotEmpty(message = "ürün tutar alanını doldurunuz!")
    @Size(min = 1, max = 100)
    private BigDecimal amount;

    private String description;
    private PublisherRequestDto publisherRequestDto;
    private AuthorRequestDto authorRequestDto;

    @NotEmpty(message = "ürün tipini seçiniz!")
    private ProductType productType;

    private CategoryRequestDto categoryRequestDto;

    @NotEmpty(message = "ürün stok bilgisi giriniz!")
    @Size(min = 1, max = 100)
    private Integer stock;
}

package com.senolkarakurt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private Long id;
    private List<ProductRequestDto> productRequestDtoList;
    private CustomerRequestDto customerRequestDto;

    public List<ProductRequestDto> getProductRequestDtoList() {
        return Objects.requireNonNullElseGet(productRequestDtoList, ArrayList::new);
    }

}

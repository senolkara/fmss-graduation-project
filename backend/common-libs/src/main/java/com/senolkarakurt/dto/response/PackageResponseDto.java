package com.senolkarakurt.dto.response;

import com.senolkarakurt.enums.PackageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PackageResponseDto implements Serializable {
    private Long id;
    private PackageType packageType;
    private BigDecimal price;
    private String description;
}

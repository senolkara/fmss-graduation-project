package com.senolkarakurt.dto.request;

import com.senolkarakurt.enums.PackageType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageRequestDto {
    private Long id;
    private PackageType packageType;
    private BigDecimal price;
    private String description;
}

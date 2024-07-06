package com.senolkarakurt.dto.request;

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
public class InvoiceRequestDto {

    private Long id;

    @NotEmpty(message = "tutar alanını doldurunuz!")
    @Size(min = 2, max = 255)
    private BigDecimal totalPrice;
    private PurchaseRequestDto purchaseRequestDto;
}

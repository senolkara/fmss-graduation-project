package com.senolkarakurt.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.senolkarakurt.dto.request.BaseSearchRequestDto;
import com.senolkarakurt.dto.request.CustomerRequestDto;
import com.senolkarakurt.dto.request.PackageRequestDto;
import com.senolkarakurt.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchRequestDto extends BaseSearchRequestDto {
    private String orderCode;
    @JsonProperty("createDate")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createDate;
    private OrderStatus orderStatus;
    private PackageRequestDto packageRequestDto;
    private CustomerRequestDto customerRequestDto;
    private String sort;
}

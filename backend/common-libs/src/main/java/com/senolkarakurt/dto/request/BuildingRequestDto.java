package com.senolkarakurt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingRequestDto {

    private Long id;

    @NotBlank(message = "ad alanını doldurunuz!")
    @Size(min = 2, max = 255)
    private String name;

    private String description;

    @NotNull(message = "metrekare alanını doldurunuz!")
    private Integer squareMeters;

    @NotNull(message = "oda sayısı alanını doldurunuz!")
    private Integer roomCount;

    @NotNull(message = "salon sayısı alanını doldurunuz!")
    private Integer saloonCount;

    @NotNull(message = "kaç katlı olduğunu doldurunuz!")
    private Integer floorCount;

    @NotNull(message = "kaç yıllık olduğunu doldurunuz!")
    private Integer howOldIsIt;

    private BuildingAddressRequestDto buildingAddressRequestDto;
    private HouseRequestDto houseRequestDto;
    private SummerHouseRequestDto summerHouseRequestDto;
    private VillaRequestDto villaRequestDto;
}

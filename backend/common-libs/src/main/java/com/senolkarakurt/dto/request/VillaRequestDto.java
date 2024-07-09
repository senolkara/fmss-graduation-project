package com.senolkarakurt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VillaRequestDto extends BuildingRequestDto {

    private boolean isThereAPool;

}

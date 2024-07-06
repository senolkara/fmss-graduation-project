package com.senolkarakurt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HouseResponseDto implements Serializable {
    private Integer whichFloor;
    private boolean isDetached;
}

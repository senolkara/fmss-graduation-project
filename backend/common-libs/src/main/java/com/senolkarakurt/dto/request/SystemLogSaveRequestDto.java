package com.senolkarakurt.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemLogSaveRequestDto {
    private LocalDateTime recordDateTime;
    private String content;
}

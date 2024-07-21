package com.senolkarakurt.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.senolkarakurt.constants.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GenericResponse<T> {

    private String message;

    @JsonProperty("dateTime")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    private HttpStatus httpStatus;
    private T data;
    private T error;

    public static GenericResponse<ExceptionResponse> failed(String message) {
        return GenericResponse.<ExceptionResponse>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .data(new ExceptionResponse(message))
                .dateTime(LocalDateTime.now())
                .error(new ExceptionResponse(message))
                .build();
    }

    public static <T> GenericResponse<T> success(T data) {
        return GenericResponse.<T>builder()
                .message(CommonConstants.SUCCESS)
                .dateTime(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .data(data)
                .build();
    }

}

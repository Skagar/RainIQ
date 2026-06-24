package com.rainiq.rainfallservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {
    private String msg;
    private int status;
    private LocalDateTime timestamp;
    private String error;
    private String path;

}

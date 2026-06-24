package com.rainiq.rainfallservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RainfallDataFetchException.class)
    public ResponseEntity<?> rainfallDataFetchException(RainfallDataFetchException exception, WebRequest request)
    {
        Response response= Response.builder()
                .msg(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .error("INTERNAL_SERVER_ERROR")
                .path(request.getDescription(false)).build();

        return new ResponseEntity<>(response,HttpStatus.
                INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(CoordinatesFetchException.class)
    public ResponseEntity<?> coordinatesFetchException(CoordinatesFetchException exception, WebRequest request)
    {
        Response response= Response.builder()
                .msg(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .error("INTERNAL_SERVER_ERROR")
                .path(request.getDescription(false)).build();

        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

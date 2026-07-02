package com.rainiq.monitoringservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceUnavailableException.class)
    public ErrorResponse serviceUnavailableException(ServiceUnavailableException exception, WebRequest webRequest)
    {
        ErrorResponse errorResponse=ErrorResponse.builder()
                .message(exception.getMessage())
                .error("Service_Unavailable")
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .timeStamp(LocalDateTime.now())
                .path(webRequest.getDescription(false))
                .build();
        return errorResponse;
    }
}

package com.rainiq.complianceservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientException.class)
    public ResponseEntity<?> insufficientException(InsufficientException exception, WebRequest request)
    {
        ErrorResponse response=ErrorResponse.builder()
                .msg(exception.getMessage())
                .occurredAt(LocalDateTime.now())
                .error("Insufficient value obtained")
                .path(request.getDescription(false))
                .status(HttpStatus.NOT_ACCEPTABLE.value()).build();
        return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
    }
}

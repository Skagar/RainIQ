package com.rainiq.monitoringservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<?> serviceUnavailableException(ServiceUnavailableException exception, WebRequest webRequest)
    {
        ErrorResponse errorResponse=ErrorResponse.builder()
                .message(exception.getMessage())
                .error("Service_Unavailable")
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .timeStamp(LocalDateTime.now())
                .path(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> invalidRequestException(InvalidRequestException exception,WebRequest webRequest)
    {
        ErrorResponse errorResponse=ErrorResponse.builder()
                .message(exception.getMessage())
                .error("Invalid_Request")
                .status(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .path(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> ResourceNotFoundException(ResourceNotFoundException exception,WebRequest webRequest)
    {
        ErrorResponse errorResponse=ErrorResponse.builder()
                .message(exception.getMessage())
                .error("Not_Found")
                .status(HttpStatus.NOT_FOUND.value())
                .timeStamp(LocalDateTime.now())
                .path(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationException(MethodArgumentNotValidException exception, WebRequest request) {
        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorResponse errorResponse=ErrorResponse.builder()
                .message(errorMessage)
                .error("Validation_Error")
                .status(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

package com.rainiq.complianceservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InsufficientException extends RuntimeException {
    public InsufficientException(String msg)
    {
        super(msg);
    }
}

package com.rainiq.rainfallservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RainfallDataFetchException extends RuntimeException{
    public RainfallDataFetchException(String msg)
    {
        super(msg);
    }
}

package com.rainiq.rainfallservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CoordinatesFetchException extends RuntimeException{
    public CoordinatesFetchException(String msg)
    {
        super(msg);
    }
}

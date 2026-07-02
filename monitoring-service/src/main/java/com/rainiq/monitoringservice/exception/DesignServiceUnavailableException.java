package com.rainiq.monitoringservice.exception;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends RuntimeException{
    public ServiceUnavailableException(String msg)
    {
        super(msg);
    }

}

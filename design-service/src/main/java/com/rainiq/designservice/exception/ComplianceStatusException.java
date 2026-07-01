package com.rainiq.designservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ComplianceStatusException extends RuntimeException {
    public ComplianceStatusException(String msg)
    {
        super(msg);
    }
}

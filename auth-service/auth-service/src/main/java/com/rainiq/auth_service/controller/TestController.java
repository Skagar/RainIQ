package com.rainiq.auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/api/auth/ping")
    public String ping()
    {
        return "Auth service is up\n";
    }
    @GetMapping("/api/secure/ping")
    public String SecurePing()
    {
        return "You Are Authenticated";
    }
}

package com.rainiq.auth_service.dto;


import com.rainiq.auth_service.entity.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String msg;
    private String token;
    private String name;
    private String email;
    private UserRole role;
}

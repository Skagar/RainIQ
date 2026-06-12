package com.rainiq.auth_service.service;

import com.rainiq.auth_service.dto.AuthResponse;
import com.rainiq.auth_service.dto.LoginRequest;
import com.rainiq.auth_service.dto.RegisterRequest;
import com.rainiq.auth_service.entity.User;
import com.rainiq.auth_service.exception.AuthException;
import com.rainiq.auth_service.repository.UserRepository;
import com.rainiq.auth_service.security.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

   public AuthResponse register(RegisterRequest request)
   {
        boolean check=userRepository.existsByEmail(request.getEmail());
        if(check)
            throw new AuthException("Email Already Registered");
        User user=User.builder().
                name(request.getName()).
                email(request.getEmail()).
                password(passwordEncoder.encode(request.getPassword())).
                role(request.getRole()).build();
       userRepository.save(user);
       return new AuthResponse("User Is Registered", jwtUtil.generateToken(request.getEmail()), request.getName(), request.getEmail(), request.getRole());
   }
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthException("Invalid password");
        }

        return new AuthResponse(
                "Logged in Successfully",
                jwtUtil.generateToken(user.getEmail()),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}

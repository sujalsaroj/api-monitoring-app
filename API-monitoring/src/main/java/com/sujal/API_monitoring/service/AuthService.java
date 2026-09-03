package com.sujal.API_monitoring.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sujal.API_monitoring.dto.auth.AuthResponse;
import com.sujal.API_monitoring.dto.auth.LoginRequest;
import com.sujal.API_monitoring.dto.auth.RegisterRequest;
import com.sujal.API_monitoring.repository.UserRepository;
import com.sujal.API_monitoring.entity.User;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            JWTService jwtService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

    }

    

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email Already Register");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole("USER");
        userRepository.save(user);

    }
    
    public AuthResponse login(LoginRequest request) {

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        
        String token = jwtService.generateToken(request.getEmail());

        return new AuthResponse(token);
        
    }
    
}

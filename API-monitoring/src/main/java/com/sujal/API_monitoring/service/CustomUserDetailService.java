package com.sujal.API_monitoring.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.sujal.API_monitoring.exception.UserNotFoundException;
import com.sujal.API_monitoring.repository.UserRepository;
import com.sujal.API_monitoring.entity.User;

@Service
public class CustomUserDetailService implements UserDetailsService{

    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not Found"));


        return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
        .build();
        
    }
    
}

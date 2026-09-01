package com.sujal.API_monitoring.service;
import org.springframework.stereotype.Service;
import com.sujal.API_monitoring.repository.UserRepository;
import com.sujal.API_monitoring.entity.User;
import com.sujal.API_monitoring.exception.UserNotFoundException;

import java.util.*;
@Service
public class UserService {
     
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
}

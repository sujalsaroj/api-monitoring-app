package com.sujal.API_monitoring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sujal.API_monitoring.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    

}

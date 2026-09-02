package com.sujal.API_monitoring.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    
    @NotBlank(message = "Name is required" )
    @Size(min=3 , max = 8 , message = "Name should be between 3 to 20 character")
    private String name;
    @NotBlank(message = "Email is required" )
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password is required" )
    @Size(min=8, max=20 , message = "Password should be between 8 to 20 Character")
    private String password;
    
}

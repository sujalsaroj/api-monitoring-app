package com.sujal.API_monitoring.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for User Login")
public class LoginRequest {

    @Schema(
            description = "User Email addrees",
                    example = "sujal@gmail.com"
    )
    @NotBlank(message = "Email is required" )
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
            description = "Password for an account",
                    example = "sujal@123"
    )
    @NotBlank(message = "Password is required" )
    @Size(min=8, max=20 , message = "Password should be between 8 to 20 Character")
    private String password;
    
}

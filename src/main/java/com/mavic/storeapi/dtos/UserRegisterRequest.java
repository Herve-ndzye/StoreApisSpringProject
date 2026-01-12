package com.mavic.storeapi.dtos;

import com.mavic.storeapi.validators.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 255,message = "Name must not exceed 255 characters")
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Lowercase(message = "Email must be lowercase")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 25,message = "Password must be between 8 to 25 characters")
    private String password;
}

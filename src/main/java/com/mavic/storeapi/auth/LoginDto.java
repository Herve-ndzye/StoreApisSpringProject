package com.mavic.storeapi.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "Email can not be blank.")
    @Email
    private String email;
    @NotBlank(message = "Password Can not be blank.")
    private String password;
}

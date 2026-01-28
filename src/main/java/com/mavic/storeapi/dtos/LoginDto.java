package com.mavic.storeapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "Email can not be blank.")
    @Email
    private String email;
    @NotBlank(message = "Password Can not be blank.")
    private String password;
}

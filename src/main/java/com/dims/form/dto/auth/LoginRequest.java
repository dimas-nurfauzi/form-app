package com.dims.form.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "The email field is required.")
    @Email(message = "The email must be a valid email address.")
    private String email;

    @NotBlank(message = "The password field is required.")
    @Size(min = 5, message = "The password must be at least 5 characters.")
    private String password;
}
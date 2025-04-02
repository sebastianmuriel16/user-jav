package com.practica.dto;

import com.practica.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@UniqueEmail
public class UserDTO {

    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @NotNull(message = "email is required")
    @Email(message = "the email must be valid")

    private String email;
}

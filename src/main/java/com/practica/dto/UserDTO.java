package com.practica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDTO {

    private UUID id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "lastname is required")
    private String lastname;

    @NotNull(message = "email is required")
    @Email(message = "the email must be valid")
    @NotBlank(message = "email is required")
    private String email;
}

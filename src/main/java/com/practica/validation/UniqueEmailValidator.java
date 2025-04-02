package com.practica.validation;

import com.practica.dto.UserDTO;
import com.practica.entity.User;
import com.practica.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, UserDTO> {

    private final UserRepository userRepository;

    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isBlank()) {
            return true; // Se permite si otro validador maneja @NotBlank
        }

        // Buscar un usuario con el mismo email
        User existingUser = userRepository.findByEmail(userDTO.getEmail());

        // Si el email no existe, es válido
        if (existingUser == null) {
            return true;
        }

        // Permitir si el email pertenece al mismo usuario
        return existingUser.getId().equals(userDTO.getId());
    }
}
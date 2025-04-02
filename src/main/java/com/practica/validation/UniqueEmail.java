package com.practica.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = UniqueEmailValidator.class) // Apunta al validador
@Target({ElementType.TYPE}) // Se aplica a toda la clase (no solo a un campo)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueEmail {
    String message() default "El correo electrónico ya está en uso";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
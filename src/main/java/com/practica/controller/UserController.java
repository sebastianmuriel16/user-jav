package com.practica.controller;

import com.practica.dto.UserDTO;
import com.practica.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class UserController {

    public static final String USER_PATH = "/api/v1/user";
    public static final String USER_PATH_ID = USER_PATH + "/{idUser}";

    private final UserService userService;

    @GetMapping(USER_PATH)
    public List<UserDTO> listUsers() {
        return userService.listUsers();
    }

    @PostMapping(USER_PATH)
    public ResponseEntity createUser(@Valid @RequestBody UserDTO user) {
        UserDTO newUser = userService.saveNewUser(user);

        return new ResponseEntity(newUser, HttpStatus.CREATED);
    }

    @GetMapping(USER_PATH_ID)
    public UserDTO getUserById(@PathVariable("idUser") UUID id) {
        UserDTO foundUser = userService.getUserById(id).orElse(null);

        return foundUser;
    }

    @PutMapping(USER_PATH_ID)
    public ResponseEntity updateUser(@PathVariable("idUser") UUID id, @Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(id); // Asegurar que el ID en el DTO coincide con la URL
        UserDTO updatedUser = userService.updateUserById(id, userDTO);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping(USER_PATH_ID)
    public ResponseEntity<Void> deleteuser(@PathVariable("idUser") UUID id) {
        userService.deleteUserById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}

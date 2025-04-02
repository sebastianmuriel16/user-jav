package com.practica.service;

import com.practica.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserDTO> listUsers();

    Optional<UserDTO> getUserById(UUID id);

    UserDTO saveNewUser(UserDTO user);

    UserDTO updateUserById(UUID id, UserDTO userDTO);

    void deleteUserById(UUID id);

}

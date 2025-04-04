package com.practica.service;

import com.practica.dto.UserDTO;
import com.practica.entity.User;
import com.practica.exception.CustomValidationException;
import com.practica.exception.NotFoundException;
import com.practica.mappers.UserMapper;
import com.practica.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> listUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(UUID id) {
        User foundUser = userRepository.findById(id).orElse(null);
        UserDTO userDTO = userMapper.userToUserDto(foundUser);
        return Optional.ofNullable(userDTO);

    }

    @Override
    public UserDTO saveNewUser(UserDTO user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomValidationException("This email is already being used by another user");
        }

        User savedUser = userMapper.userDtoToUser(user);
        userRepository.save(savedUser);
        UserDTO userDTO = userMapper.userToUserDto(savedUser);

        return userDTO;
    }

    @Override
    public UserDTO updateUserById(UUID id, UserDTO userDTO) {

        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID: " + id + " found"));

        if (!foundUser.getEmail().equals(userDTO.getEmail()) && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new CustomValidationException("Email already used");
        }

        foundUser.setName(userDTO.getName() != null ? userDTO.getName() : foundUser.getName());
        foundUser.setLastname(userDTO.getLastname() != null ? userDTO.getLastname() : foundUser.getLastname());
        foundUser.setEmail(userDTO.getEmail() != null ? userDTO.getEmail() : foundUser.getEmail());

        userRepository.save(foundUser);

        return userMapper.userToUserDto(foundUser);
    }

    @Override
    public void deleteUserById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with ID: " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}

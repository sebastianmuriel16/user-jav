package com.practica.service;

import com.practica.dto.UserDTO;
import com.practica.entity.User;
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
        User savedUser = userMapper.userDtoToUser(user);
        userRepository.save(savedUser);
        UserDTO userDTO = userMapper.userToUserDto(savedUser);

        return userDTO;
    }

    @Override
    public UserDTO updateUserById(UUID id, UserDTO userDTO) {

        return userRepository.findById(id).
                map(foundUser -> {
                    foundUser.setName(userDTO.getName());
                    foundUser.setLastname(userDTO.getLastname());
                    foundUser.setEmail(userDTO.getEmail());

                    userRepository.save(foundUser);
                    return userMapper.userToUserDto(foundUser);

                }).orElseThrow(() -> new RuntimeException("User with ID: " + id + " not found"));

    }

    @Override
    public void deleteUserById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID: " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}

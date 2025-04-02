package com.practica.mappers;

import com.practica.dto.UserDTO;
import com.practica.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User userDtoToUser(UserDTO dto);

    UserDTO userToUserDto(User user);

}

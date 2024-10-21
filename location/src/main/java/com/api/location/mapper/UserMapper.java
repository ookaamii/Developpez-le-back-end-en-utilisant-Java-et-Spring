package com.api.location.mapper;

import com.api.location.model.User;
import com.api.location.model.dto.RegisterDTO;
import com.api.location.model.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  // Mappe tous les champs de l'entité User vers UserDTO
  UserDTO userToUserDTO(User user);

  // Mappe les champs de UserDTO vers User
  User userDTOToUser(UserDTO userDTO);

  User userRegisterDTOToUser(RegisterDTO registerDTO);

}

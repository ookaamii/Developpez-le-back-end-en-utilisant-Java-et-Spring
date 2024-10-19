package com.api.location.service;

import com.api.location.model.dto.LoginDTO;
import com.api.location.model.dto.UserDTO;
import com.api.location.model.dto.AuthDataDTO;

public interface UserService {

  AuthDataDTO addUser(UserDTO userDTO);
  AuthDataDTO logging(LoginDTO loginDTO);
  UserDTO getProfilUser();
  UserDTO getUserById(int id);

}

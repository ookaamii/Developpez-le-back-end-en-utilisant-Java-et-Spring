package com.api.location.service;

import com.api.location.model.dto.request.LoginDTO;
import com.api.location.model.dto.request.RegisterDTO;
import com.api.location.model.dto.response.UserDTO;
import com.api.location.model.dto.response.AuthDataDTO;

public interface UserService {

  AuthDataDTO addUser(RegisterDTO registerDTO);
  AuthDataDTO logging(LoginDTO loginDTO);
  UserDTO getProfilUser();
  UserDTO getUserById(int id);

}

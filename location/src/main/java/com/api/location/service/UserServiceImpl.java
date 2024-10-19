package com.api.location.service;

import com.api.location.mapper.UserMapper;
import com.api.location.model.User;
import com.api.location.model.dto.LoginDTO;
import com.api.location.model.dto.UserDTO;
import com.api.location.model.dto.AuthDataDTO;
import com.api.location.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;
  private final PasswordEncoder encoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserMapper userMapper;

  @Override
  public AuthDataDTO addUser(UserDTO userDTO) {
    // Encode password before saving the user
    // mettre le DTO à la place de user
    // faire les verifs : si email existant = erreur 400 avant

    User user = userMapper.userDTOToUser(userDTO);

    user.setPassword(encoder.encode(user.getPassword()));
    repository.save(user);
    // dans le DTO mettre directement le token, pas besoin de map

   /* Map<String, Object> authData = new HashMap<>();
    authData.put("token", jwtService.generateToken(user.getEmail()));*/

    // ne pas mettre de ResponseEntity dans les services, seulement dans le controller
    return new AuthDataDTO(jwtService.generateToken(user.getEmail()));
  }

  @Override
  public AuthDataDTO logging(LoginDTO loginDTO) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
    );

    // Si l'authentification réussit, on retourne le token
    return new AuthDataDTO(jwtService.generateToken(loginDTO.getEmail()));
  }

  // Méthode pour obtenir les informations de l'utilisateur connecté
  @Override
  public UserDTO getProfilUser() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String email = userDetails.getUsername(); // Dans ce cas, c'est l'email

    // Recherche de l'utilisateur en base de données
    User user = repository.findByEmail(email);

    // Mapper l'entité User vers UserDTO pour renvoyer les infos au client
    return userMapper.userToUserDTO(user);
  }

  @Override
  public UserDTO getUserById(int id) {
    return repository.findById(id)
      .map(userMapper::userToUserDTO)
      .orElse(null);
  }

}

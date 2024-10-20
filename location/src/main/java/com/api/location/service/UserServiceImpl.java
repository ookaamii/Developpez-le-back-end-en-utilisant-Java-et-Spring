package com.api.location.service;

import com.api.location.mapper.UserMapper;
import com.api.location.model.User;
import com.api.location.model.dto.LoginDTO;
import com.api.location.model.dto.RegisterDTO;
import com.api.location.model.dto.UserDTO;
import com.api.location.model.dto.AuthDataDTO;
import com.api.location.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
  public AuthDataDTO addUser(RegisterDTO registerDTO) {
    if (repository.existsByEmail(registerDTO.getEmail())) {
      throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà.");
    }

    User user = userMapper.userRegisterDTOToUser(registerDTO);
    user.setPassword(encoder.encode(user.getPassword()));
    repository.save(user);

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
      .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
  }

}

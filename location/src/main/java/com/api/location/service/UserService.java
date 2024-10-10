package com.api.location.service;

import com.api.location.mapper.UserMapper;
import com.api.location.model.User;
import com.api.location.model.UserDTO;
import com.api.location.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserMapper userMapper;

  public String addUser(User user) {
    // Encode password before saving the user
    user.setPassword(encoder.encode(user.getPassword()));
    repository.save(user);
    return "User Added Successfully";
  }

  public ResponseEntity<?> logging(User userDto) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
    );
    if (authentication.isAuthenticated()) {
      Map<String, Object> authData = new HashMap<>();
      authData.put("token", jwtService.generateToken(userDto.getEmail()));
      authData.put("type", "Bearer");
      return ResponseEntity.ok(authData);
    } else {
      throw new UsernameNotFoundException("Invalid user request!");
    }
  }

  // Méthode pour obtenir les informations de l'utilisateur connecté
  public UserDTO getProfilUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String email = userDetails.getUsername(); // Dans ce cas, c'est l'email

    // Recherche de l'utilisateur en base de données
    User user = repository.findByEmail(email);
    if (user == null) {
      throw new RuntimeException("User not found");
    }

    // Mapper l'entité User vers UserDTO pour renvoyer les infos au client
    return userMapper.userToUserDTO(user);
  }
}

package com.api.location.service;

import com.api.location.mapper.UserMapper;
import com.api.location.model.RentalDTO;
import com.api.location.model.User;
import com.api.location.model.UserDTO;
import com.api.location.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final PasswordEncoder encoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserMapper userMapper;

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

  public UserDTO getUserById(int id) {
    return repository.findById(id)
      .map(userMapper::userToUserDTO)
      .orElse(null);
  }

}

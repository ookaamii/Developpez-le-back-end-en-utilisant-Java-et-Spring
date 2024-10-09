package com.api.location.controller;

import com.api.location.model.User;
import com.api.location.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;

  @PostMapping("/api/auth/register")
  public String register(@RequestBody User user) {
    return userService.addUser(user);
  }

  @PostMapping("/api/auth/login")
  public ResponseEntity<?> authenticateAndGetToken(@RequestBody User user) { return userService.logging(user); }

  @GetMapping("/api/auth/me")
  public User userProfile() { return userService.getProfilUser(); }

}

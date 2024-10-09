package com.api.location.service;

import com.api.location.model.User;
import com.api.location.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email); // Assuming 'email' is used as username

    if(user == null) {
      throw new UsernameNotFoundException("User not found with mail : " + email);
    }
    return new org.springframework.security.core.userdetails.User(
      user.getEmail(),
      user.getPassword(),
      Collections.emptyList());
  }
}

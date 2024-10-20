package com.api.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.location.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByEmail(String email);
  boolean existsByEmail(String email);
}

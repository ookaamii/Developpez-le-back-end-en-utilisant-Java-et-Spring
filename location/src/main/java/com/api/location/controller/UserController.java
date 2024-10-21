package com.api.location.controller;

import com.api.location.model.User;
import com.api.location.model.dto.UserDTO;
import com.api.location.service.JwtService;
import com.api.location.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Utilisateurs Controller", description = "Gère les infos d'utilisateur")
public class UserController {

  @Autowired
  private JwtService jwtService;
  private final UserServiceImpl userServiceImpl;

  @GetMapping("/user/{id}")
  @Operation(summary = "Afficher des infos utilisateurs")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
      schema = @Schema(implementation = User.class))}),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object")))
  })
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
    jwtService.checkAuthentication();
    UserDTO user = userServiceImpl.getUserById(id);
    return ResponseEntity.ok(user);
  }

}

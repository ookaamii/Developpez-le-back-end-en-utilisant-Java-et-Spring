package com.api.location.controller;

import com.api.location.model.User;
import com.api.location.model.dto.ResponseDTO;
import com.api.location.model.dto.LoginDTO;
import com.api.location.model.dto.UserDTO;
import com.api.location.model.dto.AuthDataDTO;
import com.api.location.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentification Controller", description = "Gère l'authentification")
public class AuthController {

  private final UserServiceImpl userServiceImpl;

  @PostMapping("/auth/register")
  @Operation(summary = "Enregistre un utilisateur")
  @ApiResponses({
    @ApiResponse(responseCode = "200",
      description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(type = "object",
      example = "{\"token\": \"jwt\"}"))
    }),
    @ApiResponse(responseCode = "400", description = "Erreur requête", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object", example = "{}")))
  })
  public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
    if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
      return ResponseEntity.badRequest().body(new HashMap<>());
    }
    return ResponseEntity.ok(userServiceImpl.addUser(userDTO));
  }

  @PostMapping("/auth/login")
  @Operation(summary = "Connecte un utilisateur")
  @ApiResponses({
    @ApiResponse(responseCode = "200",
      description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(type = "object",
      example = "{\"token\": \"jwt\"}"))
    }),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object", example = "{\"message\": \"error\"}")))
  })
  public ResponseEntity<?> authenticateAndGetToken(@RequestBody LoginDTO loginDTO) {
    try {
      AuthDataDTO authData = userServiceImpl.logging(loginDTO);
      return ResponseEntity.ok(authData);
    } catch (AuthenticationException e) {
      // Retourne une réponse 401 avec un DTO personnalisé
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO("error"));
    }
  }

  @GetMapping("/auth/me")
  @Operation(summary = "Profil de l'utilisateur")
  @ApiResponses({
    @ApiResponse(responseCode = "200",
      description = "OK", content = {@Content(mediaType = "application/json",
      schema = @Schema(implementation = User.class))}
    ),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object", example = "{}")))
  })
  public ResponseEntity<?> userProfile() {
    try {
      UserDTO userDTO = userServiceImpl.getProfilUser();
      return ResponseEntity.ok(userDTO);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
    }
  }

}

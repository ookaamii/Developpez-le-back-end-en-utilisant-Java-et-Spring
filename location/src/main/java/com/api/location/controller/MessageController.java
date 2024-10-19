package com.api.location.controller;

import com.api.location.model.dto.MessageDTO;
import com.api.location.model.dto.ResponseDTO;
import com.api.location.service.MessageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Messages Controller", description = "Gère l'envoi de messages")
public class MessageController {

  private final MessageServiceImpl messageServiceImpl;

  @PostMapping("/messages")
  @Operation(summary = "Enregistre un message")
  @ApiResponses({
    @ApiResponse(responseCode = "200",
      description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(type = "object",
      example = "{\"message\": \"Message send with success\"}"))
    }),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object"))),
    @ApiResponse(responseCode = "400", description = "Erreur requête", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object", example = "{}")))
  })
  public ResponseEntity<?> addMessage(@RequestBody MessageDTO messageDTO) {
    try {
      ResponseDTO response = messageServiceImpl.addMessage(messageDTO);
      return ResponseEntity.ok(response);
    } catch (AuthenticationServiceException e) {
      // Retourne une réponse 401 avec un DTO personnalisé
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (IllegalArgumentException e) {
      // Retourne une réponse 400 avec un objet vide
      return ResponseEntity.badRequest().body("{}");
    }
  }

}

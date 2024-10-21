package com.api.location.controller;

import com.api.location.model.dto.MessageDTO;
import com.api.location.model.dto.ResponseDTO;
import com.api.location.service.JwtService;
import com.api.location.service.MessageServiceImpl;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Messages Controller", description = "Gère l'envoi de messages")
public class MessageController {

  @Autowired
  private JwtService jwtService;
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
    jwtService.checkAuthentication(); // Vérification de l'authentification
    ResponseDTO response = messageServiceImpl.addMessage(messageDTO);
    return ResponseEntity.ok(response);
  }

}

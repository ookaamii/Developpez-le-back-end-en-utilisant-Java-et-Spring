package com.api.location.configuration;

import com.api.location.model.dto.ResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Erreur authentification 401
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ResponseDTO> handleUnauthorizedException(UnauthorizedException ex) {
    ResponseDTO response = new ResponseDTO();
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  // Erreur entité non trouvée
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ResponseDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
    ResponseDTO response = new ResponseDTO();
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  // Erreur mauvaise requête 400
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body("{}");
  }

}

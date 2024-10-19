package com.api.location.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomErrorEntryPoint implements AuthenticationEntryPoint {

  // Gestion des erreurs 401 (Unauthorized)
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Erreur 401 : Non autorisé, authentification requise");
  }

  // Gestion des erreurs 400 (Bad Request)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public void handleBadRequest(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex) throws IOException {
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.setContentType("application/json");

    // Créer une réponse JSON pour décrire l'erreur
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });

    // Convertir les erreurs en JSON et les envoyer dans la réponse
    String jsonResponse = errors.toString();
    response.getWriter().write(jsonResponse);
  }

  // Gestion générique (facultative) pour d'autres exceptions
  @ExceptionHandler(Exception.class)
  public void handleOtherExceptions(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
    // Laisser Spring gérer les autres exceptions par défaut en renvoyant simplement
    // l'erreur d'origine avec un message par défaut
    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du serveur");
  }
}

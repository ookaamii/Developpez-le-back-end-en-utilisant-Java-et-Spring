package com.api.location.service;

import com.api.location.mapper.MessageMapper;
import com.api.location.model.Message;
import com.api.location.model.MessageDTO;
import com.api.location.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final MessageRepository repository;
  private final MessageMapper messageMapper;

  public ResponseEntity<?> addMessage(MessageDTO messageDTO) {
    // Vérifie l'authentification de l'utilisateur
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Retourne 401
    }

    // Vérification de la validité du message
    if (messageDTO.getMessage() == null || messageDTO.getMessage().isEmpty()) {
      return ResponseEntity.badRequest().build(); // Retourne 400
    }

    Message message = messageMapper.messageDTOToMessage(messageDTO);
    repository.save(message);

    Map<String, Object> respData = new HashMap<>();
    respData.put("message", "Message send with success");
    return ResponseEntity.ok(respData);
  }
}

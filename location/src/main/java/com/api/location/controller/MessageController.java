package com.api.location.controller;

import com.api.location.model.MessageDTO;
import com.api.location.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

  private final MessageService messageService;

  @PostMapping("/messages")
  public ResponseEntity<?> addMessage(@RequestBody MessageDTO messageDTO) {
    return messageService.addMessage(messageDTO);
  }

}

package com.api.location.service;

import com.api.location.model.dto.MessageDTO;
import com.api.location.model.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface MessageService {

  ResponseDTO addMessage(MessageDTO messageDTO);

}

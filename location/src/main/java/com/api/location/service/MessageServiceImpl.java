package com.api.location.service;

import com.api.location.mapper.MessageMapper;
import com.api.location.model.Message;
import com.api.location.model.dto.MessageDTO;
import com.api.location.model.dto.ResponseDTO;
import com.api.location.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepository repository;
  private final MessageMapper messageMapper;

  @Override
  public ResponseDTO addMessage(MessageDTO messageDTO) {
    // Vérification de la validité du message
    if (messageDTO.getMessage() == null || messageDTO.getMessage().isEmpty()) {
      throw new IllegalArgumentException();
    }

    // Sauvegarde du message
    Message message = messageMapper.messageDTOToMessage(messageDTO);
    repository.save(message);

    return new ResponseDTO("Message send with success");
  }
}

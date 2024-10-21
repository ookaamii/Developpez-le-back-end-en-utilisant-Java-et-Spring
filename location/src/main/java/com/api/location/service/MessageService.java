package com.api.location.service;

import com.api.location.model.dto.MessageDTO;
import com.api.location.model.dto.ResponseDTO;

public interface MessageService {

  ResponseDTO addMessage(MessageDTO messageDTO);

}

package com.api.location.service;

import com.api.location.model.dto.request.MessageDTO;
import com.api.location.model.dto.response.ResponseDTO;

public interface MessageService {

  ResponseDTO addMessage(MessageDTO messageDTO);

}

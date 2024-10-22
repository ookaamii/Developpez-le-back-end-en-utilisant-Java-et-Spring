package com.api.location.service;

import com.api.location.model.dto.request.CreateRentalDTO;
import com.api.location.model.dto.request.RentalDTO;
import com.api.location.model.dto.response.ResponseDTO;

import java.io.IOException;
import java.util.List;

public interface RentalService {

  List<RentalDTO> getRentals();
  ResponseDTO addRental(CreateRentalDTO rentalCreateDTO) throws IOException;
  ResponseDTO updateRental(Long id, RentalDTO rentalDTO);
  RentalDTO getRentalById(Long id);

}

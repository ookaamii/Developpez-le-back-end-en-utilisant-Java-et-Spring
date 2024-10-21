package com.api.location.service;

import com.api.location.model.dto.CreateRentalDTO;
import com.api.location.model.dto.RentalDTO;
import com.api.location.model.dto.ResponseDTO;

import java.io.IOException;
import java.util.List;

public interface RentalService {

  List<RentalDTO> getRentals();
  ResponseDTO addRental(CreateRentalDTO rentalCreateDTO) throws IOException;
  ResponseDTO updateRental(Long id, RentalDTO rentalDTO);
  RentalDTO getRentalById(Long id);

}

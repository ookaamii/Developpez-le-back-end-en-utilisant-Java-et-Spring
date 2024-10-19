package com.api.location.service;

import com.api.location.model.dto.RentalDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RentalService {

  List<RentalDTO> getRentals();
    ResponseEntity<?> addRental(RentalDTO rentalDTO);
    ResponseEntity<?> updateRental(Long id, RentalDTO rentalDTO);
    RentalDTO getRentalById(Long id);

}

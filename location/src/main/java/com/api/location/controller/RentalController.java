package com.api.location.controller;

import com.api.location.model.RentalDTO;
import com.api.location.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RentalController {

  private final RentalService rentalService;

  @GetMapping("/rentals")
  public ResponseEntity<?> showAll() {
    return rentalService.getRentals();
  }

  @PostMapping("/rentals")
  public ResponseEntity<?> addRental(@RequestBody RentalDTO rentalDTO) {
    return rentalService.addRental(rentalDTO);
  }

  @GetMapping("/rentals/{id}")
  public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
    RentalDTO rental = rentalService.getRentalById(id);
    if (rental != null) {
      return ResponseEntity.ok(rental);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/rentals/{id}")
  public ResponseEntity<?> updateRental(@PathVariable Long id, @RequestBody RentalDTO rentalDTO) {
    return rentalService.updateRental(id, rentalDTO);
  }

}

package com.api.location.service;

import com.api.location.mapper.RentalMapper;
import com.api.location.model.Rental;
import com.api.location.model.RentalDTO;
import com.api.location.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {

  private final RentalRepository repository;

  private final RentalMapper rentalMapper;

  public ResponseEntity<?> getRentals() {
    List<RentalDTO> rentals = repository.findAll().stream()
      .map(rentalMapper::rentalToRentalDTO)
      .collect(Collectors.toList());

    // Créer une réponse encadrée par le champ "rentals"
    Map<String, List<RentalDTO>> response = new HashMap<>();
    response.put("rentals", rentals);

    return ResponseEntity.ok(response);
  }

  public ResponseEntity<?> addRental(RentalDTO rentalDTO) {
    Rental rental = rentalMapper.rentalDTOToRental(rentalDTO);
    repository.save(rental);

    Map<String, Object> respData = new HashMap<>();
    respData.put("message", "Rental created !");
    return ResponseEntity.ok(respData);
  }

  public ResponseEntity<?> updateRental(Long id, RentalDTO rentalDTO) {
    return repository.findById(id)
      .map(existingRental -> {
        // Mettre à jour les propriétés existantes
        existingRental.setName(rentalDTO.getName());
        existingRental.setSurface(rentalDTO.getSurface());
        existingRental.setPrice(rentalDTO.getPrice());
        existingRental.setPicture(rentalDTO.getPicture());
        existingRental.setDescription(rentalDTO.getDescription());
        existingRental.setOwnerId(rentalDTO.getOwnerId());

        // Sauvegarder et mapper à RentalDTO
        rentalMapper.rentalToRentalDTO(repository.save(existingRental));
        Map<String, Object> respData = new HashMap<>();
        respData.put("message", "Rental updated !");
        return ResponseEntity.ok(respData); // Retourne le DTO mis à jour
      })
      .orElse(ResponseEntity.notFound().build());

  }

  public RentalDTO getRentalById(Long id) {
    return repository.findById(id)
      .map(rentalMapper::rentalToRentalDTO)
      .orElse(null);
  }

}

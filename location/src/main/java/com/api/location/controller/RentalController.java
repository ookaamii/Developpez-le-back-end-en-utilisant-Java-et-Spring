package com.api.location.controller;

import com.api.location.model.Rental;
import com.api.location.model.dto.RentalDTO;
import com.api.location.model.dto.RentalsResponseDTO;
import com.api.location.service.RentalServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Location Controller", description = "Gère les locations")
public class RentalController {

  private final RentalServiceImpl rentalServiceImpl;

  @GetMapping("/rentals")
  @Operation(summary = "Affiche les locations")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Rental.class))}),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object")))
  })
  public ResponseEntity<RentalsResponseDTO> showAll() {
    try {
      List<RentalDTO> rentals = rentalServiceImpl.getRentals();
      RentalsResponseDTO response = new RentalsResponseDTO(rentals);
      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping("/rentals")
  @Operation(summary = "Enregistre une location")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "message", example = "Rental created !"))),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object")))
  })
  public ResponseEntity<?> addRental(@ModelAttribute RentalDTO rentalDTO) {
    return rentalServiceImpl.addRental(rentalDTO);
  }
/*
  @PostMapping("/rentals")
  public ResponseEntity<?> addRental(@RequestParam("picture") MultipartFile picture, RentalDTO rentalDTO) throws IOException {
    System.out.println("test");
    return rentalService.addRental(picture, rentalDTO);
  }*/

  @GetMapping("/rentals/{id}")
  @Operation(summary = "Affiche une location")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
      schema = @Schema(implementation = Rental.class))}),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object")))
  })
  public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
    RentalDTO rental = rentalServiceImpl.getRentalById(id);
    if (rental != null) {
      return ResponseEntity.ok(rental);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/rentals/{id}")
  @Operation(summary = "Modifie une location")
  @ApiResponses({
    @ApiResponse(responseCode = "400", description = "OK", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "message", example = "Rental updated !"))),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object")))
  })
  public ResponseEntity<?> updateRental(@PathVariable Long id, @RequestBody RentalDTO rentalDTO) {
    return rentalServiceImpl.updateRental(id, rentalDTO);
  }

}

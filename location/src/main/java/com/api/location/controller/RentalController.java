package com.api.location.controller;

import com.api.location.model.dto.CreateRentalDTO;
import com.api.location.model.dto.RentalDTO;
import com.api.location.model.dto.RentalsResponseDTO;
import com.api.location.model.dto.ResponseDTO;
import com.api.location.service.JwtService;
import com.api.location.service.RentalServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Location Controller", description = "Gère les locations")
public class RentalController {

  @Autowired
  private JwtService jwtService;
  private final RentalServiceImpl rentalServiceImpl;

  @GetMapping("/rentals")
  @Operation(summary = "Affiche les locations")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = RentalsResponseDTO.class))}),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object")))
  })
  public ResponseEntity<RentalsResponseDTO> showAll() {
    jwtService.checkAuthentication();
    List<RentalDTO> rentals = rentalServiceImpl.getRentals();
    RentalsResponseDTO response = new RentalsResponseDTO(rentals);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/rentals")
  @Operation(summary = "Enregistre une location")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object",
        example = "{\"message\": \"Rental created !\"}"))),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object")))
  })
  public ResponseEntity<ResponseDTO> addRental(@ModelAttribute CreateRentalDTO rentalDTO) {
    jwtService.checkAuthentication();
    return ResponseEntity.ok(rentalServiceImpl.addRental(rentalDTO));
  }

  @GetMapping("/rentals/{id}")
  @Operation(summary = "Affiche une location")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
      schema = @Schema(implementation = RentalDTO.class))}),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object")))
  })
  public ResponseEntity<?> getRentalById(@PathVariable Long id) {
    jwtService.checkAuthentication();
    return ResponseEntity.ok(rentalServiceImpl.getRentalById(id));
  }

  @PutMapping("/rentals/{id}")
  @Operation(summary = "Modifie une location")
  @ApiResponses({
    @ApiResponse(responseCode = "400", description = "OK", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object",
        example = "{\"message\": \"Rental updated !\"}"))),
    @ApiResponse(responseCode = "401", description = "Accès refusé", content = @Content(mediaType = "application/json",
      schema = @Schema(type = "object")))
  })
  public ResponseEntity<?> updateRental(@PathVariable Long id, @ModelAttribute RentalDTO rentalDTO) {
    jwtService.checkAuthentication();
    return ResponseEntity.ok(rentalServiceImpl.updateRental(id, rentalDTO));
  }

}

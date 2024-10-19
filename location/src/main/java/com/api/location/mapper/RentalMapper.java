package com.api.location.mapper;

import com.api.location.model.Rental;
import com.api.location.model.dto.RentalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalMapper {

  @Mapping(target = "picture", ignore = true) // On ignore ce champ
  Rental rentalDTOToRental(RentalDTO rentalDTO);

  @Mapping(target = "picture", ignore = true) // On ignore ce champ
  RentalDTO rentalToRentalDTO(Rental rental);

  List<RentalDTO> rentalsToRentalDTOs(List<Rental> rentals);

}

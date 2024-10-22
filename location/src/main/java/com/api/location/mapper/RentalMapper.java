package com.api.location.mapper;

import com.api.location.model.Rental;
import com.api.location.model.dto.request.CreateRentalDTO;
import com.api.location.model.dto.request.RentalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalMapper {

  @Mapping(target = "picture", source = "picture") // Mapper le champ String picture
  RentalDTO rentalToRentalDTO(Rental rental);

  @Mapping(target = "picture", source = "picture") // Mapper le champ String picture
  Rental rentalDTOToRental(RentalDTO rentalDTO);

  @Mapping(target = "picture", ignore = true) // Ignorer MultipartFile lors de la conversion
  Rental rentalCreateDTOToRental(CreateRentalDTO rentalCreateDTO);

  List<RentalDTO> rentalsToRentalDTOs(List<Rental> rentals);

}

package com.api.location.mapper;

import com.api.location.model.Rental;
import com.api.location.model.dto.CreateRentalDTO;
import com.api.location.model.dto.RentalDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

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

package com.api.location.mapper;

import com.api.location.model.Rental;
import com.api.location.model.RentalDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RentalMapper {

  RentalDTO rentalToRentalDTO(Rental rental);

  Rental rentalDTOToRental(RentalDTO rentalDTO);

}

package com.api.location.model.dto.response;

import com.api.location.model.dto.request.RentalDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RentalsResponseDTO {

    private List<RentalDTO> rentals;

}

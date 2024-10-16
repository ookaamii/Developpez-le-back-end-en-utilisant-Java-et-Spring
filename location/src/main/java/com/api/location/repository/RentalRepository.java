package com.api.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.location.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}

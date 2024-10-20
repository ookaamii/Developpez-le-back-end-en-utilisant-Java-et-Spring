package com.api.location.service;

import com.api.location.mapper.RentalMapper;
import com.api.location.model.Rental;
import com.api.location.model.User;
import com.api.location.model.dto.CreateRentalDTO;
import com.api.location.model.dto.RentalDTO;
import com.api.location.model.dto.ResponseDTO;
import com.api.location.repository.RentalRepository;
import com.api.location.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

  private final RentalRepository repository;
  private final UserRepository userRepository;
  private final RentalMapper rentalMapper;
  private static final String UPLOAD_DIR = "uploads/";
  @Value("${app.base-url}")
  private String baseUrl;

  @Override
  public List<RentalDTO> getRentals() {
    List<Rental> rentals = repository.findAll();
    return rentalMapper.rentalsToRentalDTOs(rentals);
  }

  @Override
  public ResponseDTO addRental(CreateRentalDTO rentalCreateDTO) {
    Rental rental = rentalMapper.rentalCreateDTOToRental(rentalCreateDTO);

    // On récupère l'utilisateur connecté qui vient de créer une location, pour rentrer son id en BDD
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String email = userDetails.getUsername();
    User user = userRepository.findByEmail(email);
    rental.setOwnerId(user.getId());

    // On sauvegarde l'image si elle est présente
    MultipartFile picture = rentalCreateDTO.getPicture();
    if (picture != null && !picture.isEmpty()) {
      String fileName = picture.getOriginalFilename();
      String randomFileName = generateUniqueFileName(fileName); // Générer un nom de fichier unique
      Path filePath = Paths.get("src/main/resources/uploads/" + randomFileName);

      try {
        // On crée le dossier s'il n'existe pas
        Files.createDirectories(filePath.getParent());
        Files.copy(picture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // On enregistre l'URL complète dans le champ picture de Rental
        String fileUrl = "http://localhost:3001/uploads/" + randomFileName;
        rental.setPicture(fileUrl);
      } catch (IOException e) {
        throw new RuntimeException("Erreur lors de l'enregistrement du fichier", e);
      }
    }

    repository.save(rental);

    return new ResponseDTO("Rental created !");
  }

  private String generateUniqueFileName(String originalFileName) {
    String extension = originalFileName.substring(originalFileName.lastIndexOf('.')); // Obtenir l'extension
    // Générer un nom aléatoire avec l'extension
    return UUID.randomUUID().toString() + extension;
  }

  @Override
  public ResponseDTO updateRental(Long id, RentalDTO rentalDTO) {
    return repository.findById(id)
      .map(existingRental -> {
        // On met à jour les propriétés existantes
        existingRental.setName(rentalDTO.getName());
        existingRental.setSurface(rentalDTO.getSurface());
        existingRental.setPrice(rentalDTO.getPrice());
        existingRental.setDescription(rentalDTO.getDescription());

        // Sauvegarde et map à RentalDTO
        rentalMapper.rentalToRentalDTO(repository.save(existingRental));

        return new ResponseDTO("Rental updated !");
      })
      .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
  }

  @Override
  public RentalDTO getRentalById(Long id) {
    return repository.findById(id)
      .map(rentalMapper::rentalToRentalDTO)
      .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
  }

}

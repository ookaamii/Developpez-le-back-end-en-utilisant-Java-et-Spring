package com.api.location.service;

import com.api.location.mapper.RentalMapper;
import com.api.location.model.Rental;
import com.api.location.model.User;
import com.api.location.model.dto.RentalDTO;
import com.api.location.repository.RentalRepository;
import com.api.location.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

  private final RentalRepository repository;
  private final UserRepository userRepository;
  private final RentalMapper rentalMapper;

  @Override
  public List<RentalDTO> getRentals() {

    List<Rental> rentals = repository.findAll();
    return rentalMapper.rentalsToRentalDTOs(rentals);

  }

  @Override
  public ResponseEntity<?> addRental(RentalDTO rentalDTO) {

    Rental rental = rentalMapper.rentalDTOToRental(rentalDTO);

    // Gestion de l'image
    if (rentalDTO.getPicture() != null && !rentalDTO.getPicture().isEmpty()) {
      String picturePath = saveImageToFileSystem(rentalDTO.getPicture());
      rental.setPicture(picturePath);
    }

    // On récupère l'utilisateur connectée qui vient de créer une location, pour rentrer son id en BDD
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String email = userDetails.getUsername();
    User user = userRepository.findByEmail(email);
    rental.setOwnerId(user.getId());

    // On sauvegarde le tout en BDD
    repository.save(rental);

    Map<String, Object> respData = new HashMap<>();
    respData.put("message", "Rental created successfully");
    return ResponseEntity.ok(respData);

  }

  private String saveImageToFileSystem(MultipartFile file) {

    try {
      // Chemin absolu vers le dossier "resources/images"
      String folder = new File("src/main/resources/images").getAbsolutePath();
      String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      Path imagePath = Paths.get(folder, fileName);

      // Crée les répertoires nécessaires si non existants
      Files.createDirectories(imagePath.getParent());

      // Sauvegarde le fichier sur le système de fichiers
      Files.write(imagePath, file.getBytes());
      return imagePath.toString();
    } catch (IOException e) {
      throw new RuntimeException("Échec de l'enregistrement de l'image : " + e.getMessage());
    }

  }


  /*
    public ResponseEntity<?> addRental(MultipartFile picture, RentalDTO rentalDTO) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(picture.getOriginalFilename()));
        System.out.println(fileName);
        System.out.println("bloup");
        System.out.println(picture);
        rentalDTO.setPicture(fileName);

        Rental rental = rentalMapper.rentalDTOToRental(rentalDTO);
        repository.save(rental);

        String uploadDir = "images/test";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = picture.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }

      Map<String, Object> respData = new HashMap<>();
      respData.put("message", "Rental created !");
      return ResponseEntity.ok(respData);
    }
  */

  @Override
  public ResponseEntity<?> updateRental(Long id, RentalDTO rentalDTO) {

    return repository.findById(id)
      .map(existingRental -> {
        // Mettre à jour les propriétés existantes
        existingRental.setName(rentalDTO.getName());
        existingRental.setSurface(rentalDTO.getSurface());
        existingRental.setPrice(rentalDTO.getPrice());
        existingRental.setDescription(rentalDTO.getDescription());

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

package com.api.location.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class RentalDTO {

  private Long id;

  private String name;

  private Float surface;

  private Float price;

  //private String picture;
  //il y a un soucis ici. peut-être qu'il faut laisser string picture et mettre une autre variable pour récupérerle file
  //ou faire un traitement dans les services pour refaire fichier > string
  private MultipartFile picture;

  private String description;

  @JsonProperty("owner_id")
  private Long ownerId;

  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
  private Date createdAt;

  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
  private Date updatedAt;

}

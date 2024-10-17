package com.api.location.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RentalDTO {

  private Long id;
  private String name;
  private Float surface;
  private Float price;
  private String picture;
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

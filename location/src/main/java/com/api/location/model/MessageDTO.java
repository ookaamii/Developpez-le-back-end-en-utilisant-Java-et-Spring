package com.api.location.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {

  private Long id;
  private String message;
  @JsonProperty("rental_id")
  private Long rentalId;
  @JsonProperty("user_id")
  private Long userId;
  @JsonProperty("created_at")
  private Date createdAt;
  @JsonProperty("updated_at")
  private Date updatedAt;
}

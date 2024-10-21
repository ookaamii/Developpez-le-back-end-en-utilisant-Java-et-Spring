package com.api.location.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
  @Schema(type = "string", format = "date", example = "2022/02/02", description = "La date au format yyyy/MM/dd")
  private Date createdAt;

  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
  @Schema(type = "string", format = "date", example = "2022/02/02", description = "La date au format yyyy/MM/dd")
  private Date updatedAt;

}

package com.api.location.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

  private Long id;
  private String email;
  private String name;
  @JsonProperty("created_at")
  private Date createdAt;
  @JsonProperty("updated_at")
  private Date updatedAt;

}

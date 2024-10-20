package com.api.location.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

  private Long id;

  private String name;

  private String email;

  @JsonIgnore
  private String password;

  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
  private Date createdAt;

  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
  private Date updatedAt;

}

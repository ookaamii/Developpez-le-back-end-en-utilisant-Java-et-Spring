package com.api.location.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

  private Long id;
  private String email;
  private String name;
  private Date createdAt;
  private Date updatedAt;

}

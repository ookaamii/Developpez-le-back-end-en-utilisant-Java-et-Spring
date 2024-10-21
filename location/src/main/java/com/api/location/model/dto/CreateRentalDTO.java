package com.api.location.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class CreateRentalDTO {

    private Long id;

    private String name;

    private Float surface;

    private Float price;

    private MultipartFile picture;

    private String description;

    @JsonProperty("owner_id")
    private Long ownerId;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @Schema(type = "string", format = "date", example = "2022/02/02", description = "La date au format yyyy/MM/dd")
    private Date createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @Schema(type = "string", format = "date", example = "2022/02/02", description = "La date au format yyyy/MM/dd")
    private Date updatedAt;

}

package com.unow.vehicle.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

/** The Vehicle data transfer object class. */
@Data
public class VehicleDto {

  @NotBlank(message = "brand is required")
  private String brand;

  @NotBlank(message = "model is required")
  private String model;

  @NotBlank(message = "licensePlate is required")
  private String licensePlate;

  @NotBlank(message = "color is required")
  private String color;

  @NotBlank(message = "year is required")
  @Pattern(regexp = "(^$|[0-9]{4})", message = "year must be valid")
  private String year;
}

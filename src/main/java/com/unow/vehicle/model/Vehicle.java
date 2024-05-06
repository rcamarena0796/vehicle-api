package com.unow.vehicle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The Vehicle class. */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
  @Id @GeneratedValue private Long id;

  private String brand;

  private String model;

  @Column(unique = true, nullable = false)
  private String licensePlate;

  private String color;

  private String year;
}

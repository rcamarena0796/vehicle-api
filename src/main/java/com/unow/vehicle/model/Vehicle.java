package com.unow.vehicle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Vehicle {
    @Id
    @GeneratedValue
    private Long id;

    private String brand;

    private String model;

    private String licencePlate;

    private String color;

    private String year;
}

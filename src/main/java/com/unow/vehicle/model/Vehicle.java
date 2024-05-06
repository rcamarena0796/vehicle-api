package com.unow.vehicle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;

@Data
@Entity
public class Vehicle {
    @Id
    @GeneratedValue
    private Long id;

    private String brand;

    private String model;

    @Column(unique = true, nullable = false)
    private String licensePlate;

    private String color;

    private String year;
}

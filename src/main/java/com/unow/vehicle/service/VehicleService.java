package com.unow.vehicle.service;

import com.unow.vehicle.model.Vehicle;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface VehicleService {
    Optional<Vehicle> findVehicleById(Long id);
    Vehicle createVehicle(Vehicle vehicle);
    Page<Vehicle> findVehicles(String brand, String model, String licencePlate, int page, int size, String sortBy);
    void deleteVehicle(Long id);
}

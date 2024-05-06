package com.unow.vehicle.service;

import com.unow.vehicle.model.Vehicle;
import java.util.Optional;
import org.springframework.data.domain.Page;

/**
 * The interface Vehicle service.
 */
public interface VehicleService {
  Optional<Vehicle> findVehicleById(Long id);

  Vehicle createVehicle(Vehicle vehicle);

  Page<Vehicle> findVehicles(String brand, String model, String licencePlate, int page, int size,
                             String sortBy, String id);

  void deleteVehicle(Long id);
}

package com.unow.vehicle.service.impl;

import com.unow.vehicle.model.Vehicle;
import com.unow.vehicle.repository.VehicleRepository;
import com.unow.vehicle.repository.VehicleSpecifications;
import com.unow.vehicle.service.VehicleService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * The implementation of the Vehicle service interface.
 */
@Service
public class VehicleServiceImpl implements VehicleService {
  private final VehicleRepository vehicleRepository;

  @Autowired
  public VehicleServiceImpl(VehicleRepository vehicleRepository) {
    this.vehicleRepository = vehicleRepository;
  }

  @Override
  public Optional<Vehicle> findVehicleById(Long id) {
    return vehicleRepository.findById(id);
  }

  @Override
  public Vehicle createVehicle(Vehicle vehicle) {
    return vehicleRepository.save(vehicle);
  }

  @Override
  public Page<Vehicle> findVehicles(
      String brand, String model, String licencePlate, int page, int size,
      String sortBy, String id) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

    Specification<Vehicle> spec =
        VehicleSpecifications.withDynamicQuery(brand, model, licencePlate, id);
    return vehicleRepository.findAll(spec, pageable);
  }

  @Override
  public void deleteVehicle(Long id) {
    vehicleRepository.deleteById(id);
  }
}

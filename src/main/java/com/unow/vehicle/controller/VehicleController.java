package com.unow.vehicle.controller;

import com.unow.vehicle.model.Vehicle;
import com.unow.vehicle.model.dto.VehicleDto;
import com.unow.vehicle.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** The Vehicle controller. */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/vehicle")
public class VehicleController {
  private ModelMapper modelMapper = new ModelMapper();

  @Autowired VehicleService vehicleService;

  /**
   * Gets vehicles by brand, model or license plate.
   *
   * @param brand the Vehicle brand
   * @param model the Vehicle model
   * @param licensePlate the Vehicle license plate
   * @param page the number of the page to be returned
   * @param size the size of each page
   * @param sortBy the parameter used to sort the results
   * @return the vehicles paginated
   */
  @Operation(
      description =
          "Endpoint used to list vehicles in a paginated way filtered by brand"
              + ", model or license plate")
  @GetMapping()
  public Page<Vehicle> getVehicles(
      @RequestParam(required = false) String brand,
      @RequestParam(required = false) String model,
      @RequestParam(required = false) String licensePlate,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy) {

    return vehicleService.findVehicles(brand, model, licensePlate, page, size, sortBy);
  }

  /**
   * Creates a vehicle.
   *
   * @param vehicleDto the vehicle dto
   * @return the created vehicle
   */
  @Operation(description = "Endpoint used to create a vehicle")
  @PostMapping()
  public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
    Vehicle vehicle = vehicleService.createVehicle(modelMapper.map(vehicleDto, Vehicle.class));
    return ResponseEntity.created(URI.create("/users".concat(vehicle.getId().toString())))
        .contentType(MediaType.APPLICATION_JSON)
        .body(vehicle);
  }

  /**
   * Updates a vehicle.
   *
   * @param id the Vehicle id
   * @param vehicleDetails the vehicle details
   * @return the response entity
   */
  @Operation(description = "Endpoint used to update a vehicle")
  @PutMapping("/{id}")
  public ResponseEntity<Vehicle> updateVehicle(
      @PathVariable Long id, @Valid @RequestBody VehicleDto vehicleDetails) {
    Vehicle foundUpdatedVehicle =
        vehicleService
            .findVehicleById(id)
            .map(
                existingVehicle -> {
                  Vehicle updatedVehicle = modelMapper.map(vehicleDetails, Vehicle.class);
                  updatedVehicle.setId(existingVehicle.getId());
                  return updatedVehicle;
                })
            .orElse(null);
    if (foundUpdatedVehicle == null) {
      return ResponseEntity.notFound().build();
    }
    Vehicle vehicle = vehicleService.createVehicle(foundUpdatedVehicle);
    return ResponseEntity.created(URI.create("/users".concat(vehicle.getId().toString())))
        .contentType(MediaType.APPLICATION_JSON)
        .body(vehicle);
  }

  /**
   * Deletes a vehicle.
   *
   * @param id the Vehicle id
   * @return the response entity
   */
  @Operation(description = "Endpoint used to delete a vehicle")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
    return vehicleService
        .findVehicleById(id)
        .map(
            vehicle -> {
              vehicleService.deleteVehicle(id);
              return ResponseEntity.ok().<Void>build();
            })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}

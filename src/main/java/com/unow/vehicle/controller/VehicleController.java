package com.unow.vehicle.controller;

import com.unow.vehicle.model.dto.VehicleDto;
import com.unow.vehicle.model.Vehicle;
import com.unow.vehicle.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

/**
 * Vehicle Controller.
 */
//@Api(tags = "Vehicle API", value = "CRUD operations for vehicles")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/vehicle")
public class VehicleController {
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    VehicleService vehicleService;

    @Operation(description  = "Endpoint used to list vehicles in a paginated way filtered by brand, model or license plate")
    @GetMapping()
    public Page<Vehicle> getEmployees(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String licensePlate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return vehicleService.findVehicles(brand, model, licensePlate, page, size, sortBy);
    }

    @Operation(description = "Endpoint used to create a vehicle")
    @PostMapping()
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleService.createVehicle(modelMapper.map(vehicleDto, Vehicle.class));
        return ResponseEntity
                .created(URI.create("/users".concat(vehicle.getId().toString())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(vehicle);
    }

    @Operation(description = "Endpoint used to update a vehicle")
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleDto vehicleDetails) {
        Vehicle foundUpdatedVehicle = vehicleService.findVehicleById(id)
                .map(existingVehicle -> {
                    Vehicle updatedVehicle = modelMapper.map(vehicleDetails, Vehicle.class);
                    updatedVehicle.setId(existingVehicle.getId());
                    return updatedVehicle;
                }).orElse(null);
        if(foundUpdatedVehicle==null){
            return ResponseEntity.notFound().build();
        }
        Vehicle vehicle = vehicleService.createVehicle(foundUpdatedVehicle);
        return ResponseEntity
                .created(URI.create("/users".concat(vehicle.getId().toString())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(vehicle);
    }

    @Operation(description = "Endpoint used to delete a vehicle")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        return vehicleService.findVehicleById(id)
                .map(vehicle -> {
                    vehicleService.deleteVehicle(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
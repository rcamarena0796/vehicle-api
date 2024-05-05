package com.unow.vehicle.service;

import com.unow.vehicle.dto.VehicleDto;
import com.unow.vehicle.model.Vehicle;

import java.util.List;

public interface VehicleService {
    public List<Vehicle> getVehicles();
    public Vehicle getVehicle(Long id);
    public Vehicle createVehicle(VehicleDto vehicle);
}

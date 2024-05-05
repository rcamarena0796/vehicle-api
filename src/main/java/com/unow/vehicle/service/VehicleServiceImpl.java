package com.unow.vehicle.service;

import com.unow.vehicle.dto.VehicleDto;
import com.unow.vehicle.model.Vehicle;
import com.unow.vehicle.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private ModelMapper modelMapper = new ModelMapper();
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<Vehicle> getVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle getVehicle(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    @Override
    public Vehicle createVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
        return vehicleRepository.save(vehicle);
    }
}

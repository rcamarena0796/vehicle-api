package com.unow.vehicle.controller;

import com.unow.vehicle.dto.VehicleDto;
import com.unow.vehicle.model.Vehicle;
import com.unow.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @GetMapping(value = "/hello")
    public String sayHello() {

        return "HELLO";
    }

    @PostMapping()
    public Vehicle createVehicle(@RequestBody VehicleDto vehicleDto) {
        return vehicleService.createVehicle(vehicleDto);
    }
}
package com.unow.vehicle.service;

import com.unow.vehicle.model.Vehicle;
import com.unow.vehicle.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VehicleServiceTest {

    @MockBean
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    private static Vehicle vehicle;

    private static List<Vehicle> vehicleList;

    @BeforeAll
    public static void setUp() {
        vehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle(1L, "Tesla", "S", "AAA123", "red", "2020"));
        vehicleList.add(new Vehicle(1L, "Tesla", "3", "CCC123", "green", "2021"));

        vehicle = new Vehicle(1L, "Toyota", "supra", "BBB123", "blue", "2018");

    }

    @Test
    public void createVehicleShouldReturnOk() {
        when(vehicleRepository.save(any())).thenReturn(vehicle);
        Vehicle response = vehicleService.createVehicle(vehicle);

        assertEquals(response.getId(), vehicle.getId());
        assertEquals(response.getBrand(), vehicle.getBrand());
        assertEquals(response.getModel(), vehicle.getModel());
        assertEquals(response.getColor(), vehicle.getColor());
        assertEquals(response.getYear(), vehicle.getYear());
    }

    @Test
    public void findVehicleByIdShouldReturnOk() {
        Long vehicleId = 1L;

        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));

        Optional<Vehicle> response = vehicleService.findVehicleById(vehicleId);

        assertEquals(response.get().getId(), vehicle.getId());
        assertEquals(response.get().getBrand(), vehicle.getBrand());
        assertEquals(response.get().getModel(), vehicle.getModel());
        assertEquals(response.get().getColor(), vehicle.getColor());
        assertEquals(response.get().getYear(), vehicle.getYear());
    }

    @Test
    public void deleteVehicleShouldReturnOk() {
        Long vehicleId = 1L;

        doNothing().when(vehicleRepository).deleteById(anyLong());

        vehicleService.deleteVehicle(vehicleId);

        verify(vehicleRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void findVehiclesShouldReturnOk() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Vehicle> vehiclePage = new PageImpl<>(vehicleList, pageRequest, vehicleList.size());

        when(vehicleRepository.findAll(Mockito.<Specification<Vehicle>>any(), Mockito.any(Pageable.class))).thenReturn(vehiclePage);

        Page<Vehicle> response = vehicleService.findVehicles(vehicle.getBrand(), vehicle.getModel(), vehicle.getLicensePlate(),0,10,"id");

        assertEquals(response.get().count(), vehicleList.size());

        verify(vehicleRepository, times(1)).findAll(Mockito.<Specification<Vehicle>>any(), Mockito.any(Pageable.class));
    }

}

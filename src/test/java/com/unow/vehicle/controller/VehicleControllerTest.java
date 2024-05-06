package com.unow.vehicle.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unow.vehicle.model.Vehicle;
import com.unow.vehicle.repository.VehicleRepository;
import com.unow.vehicle.service.VehicleService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    protected VehicleService service;

    @MockBean
    private VehicleRepository vehicleRepository;

    private static Vehicle vehicle;

    private static List<Vehicle> vehicleList;

    private static List<Vehicle> emptyVehicleList;

    @BeforeAll
    public static void setUp() {
        vehicleList = new ArrayList<>();
        emptyVehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle(1L, "Tesla", "S", "AAA123", "red", "2020"));
        vehicleList.add(new Vehicle(1L, "Tesla", "3", "CCC123", "green", "2021"));

        vehicle = new Vehicle(1L, "Toyota", "supra", "BBB123", "blue", "2018");

    }

    @Test
    public void getVehiclesShouldReturnOk() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Vehicle> vehiclePage = new PageImpl<>(vehicleList, pageRequest, vehicleList.size());

        when(service.findVehicles(anyString(),anyString(),anyString(),anyInt(),anyInt(),anyString(),anyString())).thenReturn(vehiclePage);
        this.mockMvc.perform(get("/api/vehicle?brand=Tesla&model=S&licensePlate=AAA&id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(mapToJson(vehicleList))));

        verify(service, times(1)).findVehicles(anyString(),anyString(),anyString(),anyInt(),anyInt(),anyString(),anyString());
    }

    @Test
    public void getVehiclesShouldReturnEmpty() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Vehicle> vehiclePage = new PageImpl<>(emptyVehicleList, pageRequest, emptyVehicleList.size());

        when(service.findVehicles(anyString(),anyString(),anyString(),anyInt(),anyInt(),anyString(),anyString())).thenReturn(vehiclePage);

        this.mockMvc.perform(get("/api/vehicle?brand=Tesla&model=S&licensePlate=AAA&id=1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(mapToJson(emptyVehicleList))));

        verify(service, times(1)).findVehicles(anyString(),anyString(),anyString(),anyInt(),anyInt(),anyString(),anyString());
    }

    @Test
    public void createVehicleShouldReturnOk() throws Exception {
        when(service.createVehicle(any())).thenReturn(vehicle);
        this.mockMvc.perform(post("/api/vehicle").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(vehicle))).andDo(print()).andExpect(status().is(201))
                .andExpect(content().string(containsString(mapToJson(vehicle))));

        verify(service, times(1)).createVehicle(any());
    }

    @Test
    public void createVehicleShouldReturnError() throws Exception {
        Vehicle badVehicle = new Vehicle(1L, "Toyota", "supra", "BBB123", "blue", "error");
        when(service.createVehicle(any())).thenReturn(badVehicle);
        this.mockMvc.perform(post("/api/vehicle").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(badVehicle))).andDo(print()).andExpect(status().isBadRequest());

        verify(service, times(0)).createVehicle(any());
    }

    @Test
    public void updateVehicleShouldReturnOk() throws Exception {
        Long vehicleId = 1L;
        when(service.findVehicleById(anyLong())).thenReturn(Optional.of(vehicle));
        when(service.createVehicle(any())).thenReturn(vehicle);
        this.mockMvc.perform(put("/api/vehicle/{id}",vehicleId).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(vehicle))).andDo(print()).andExpect(status().is(201))
                .andExpect(content().string(containsString(mapToJson(vehicle))));

        verify(service, times(1)).createVehicle(any());
        verify(service, times(1)).createVehicle(any());
    }

    @Test
    public void updateVehicleShouldReturnError() throws Exception {
        Long vehicleId = 1L;
        when(service.findVehicleById(anyLong())).thenReturn(Optional.empty());
        when(service.createVehicle(any())).thenReturn(vehicle);
        this.mockMvc.perform(put("/api/vehicle/{id}",vehicleId).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(vehicle))).andDo(print()).andExpect(status().isNotFound());

        verify(service, times(1)).findVehicleById(anyLong());
        verify(service, times(0)).createVehicle(any());
    }

    @Test
    public void testDeleteShouldReturnOk() throws Exception {
        Long vehicleId = 1L;
        when(service.findVehicleById(anyLong())).thenReturn(Optional.of(vehicle));
        doNothing().when(service).deleteVehicle(anyLong());

        mockMvc.perform(
                        delete("/api/vehicle/{id}", vehicleId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteVehicle(anyLong());
        verify(service, times(1)).findVehicleById(anyLong());
    }


    @Test
    public void testDeleteShouldReturnError() throws Exception {
        Long vehicleId = 1L;
        when(service.findVehicleById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(
                        delete("/api/vehicle/{id}", vehicleId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findVehicleById(anyLong());
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}

package com.oasis.springboot.controller;

import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import com.oasis.springboot.dto.plant.PlantsResponseDto;
import com.oasis.springboot.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/plants")
@RestController
public class PlantController {
    private final PlantService plantService;

    @PostMapping("")
    public void savePlant(@RequestBody PlantSaveRequestDto requestDto) {
        plantService.savePlant(requestDto);
    }

    @GetMapping("")
    public List<PlantsResponseDto> getPlants(){
        return plantService.getPlants(1L);
    }

    @DeleteMapping("/{plantId}")
    public void deletePlant(@PathVariable Long plantId) {
        plantService.deletePlant(plantId);
    }
}

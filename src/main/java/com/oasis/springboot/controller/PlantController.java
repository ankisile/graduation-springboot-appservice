package com.oasis.springboot.controller;

import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import com.oasis.springboot.dto.plant.PlantsResponseDto;
import com.oasis.springboot.response.ListResponse;
import com.oasis.springboot.response.ResponseService;
import com.oasis.springboot.response.SingleResponse;
import com.oasis.springboot.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Plant", description = "Plant 등록, 전체 Plants 조회 API -> 삭제는 일단 하지말아보샘")
@RequiredArgsConstructor
@RequestMapping("/api/plants")
@RestController
public class PlantController {
    private final PlantService plantService;
    private final ResponseService responseService;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("")
    public SingleResponse<String> savePlant(@RequestBody PlantSaveRequestDto requestDto) {
        return responseService.getSingleResponse(plantService.savePlant(requestDto));
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("")
    public ListResponse<PlantsResponseDto> getPlants(){
        return responseService.getListResponse(plantService.getPlants());
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @DeleteMapping("/{plantId}")
    public void deletePlant(@PathVariable Long plantId) {
        plantService.deletePlant(plantId);
    }
}

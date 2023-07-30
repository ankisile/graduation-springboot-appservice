package com.oasis.springboot.controller;

import com.oasis.springboot.common.response.ListResponse;
import com.oasis.springboot.common.response.ResponseService;
import com.oasis.springboot.common.response.SingleResponse;
import com.oasis.springboot.dto.plant.PlantDetailResponseDto;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import com.oasis.springboot.dto.plant.PlantsResponseDto;
import com.oasis.springboot.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Tag(name = "Plant")
@RequiredArgsConstructor
@RequestMapping("/api/plants")
@RestController
public class PlantController {
    private final PlantService plantService;
    private final ResponseService responseService;

    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SingleResponse<Long> savePlant(@ModelAttribute @Valid PlantSaveRequestDto requestDto) {
        return responseService.getSingleResponse(plantService.savePlant(requestDto));
    }

    @Operation(summary = "get plants", description = "식물 조회 -> 홈화면, 식물일지 부분에서 쓰면 됨", security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("")
    public ListResponse<PlantsResponseDto> getPlants() {
        return responseService.getListResponse(plantService.getPlants());
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("/{plantId}")
    public SingleResponse<String> deletePlant(@PathVariable Long plantId) {
        return responseService.getSingleResponse(plantService.deletePlant(plantId));
    }

    @Operation(description = "식물 디테일", security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/{plantId}")
    public SingleResponse<PlantDetailResponseDto> getPlantDetail(@PathVariable Long plantId) {
        return responseService.getSingleResponse(plantService.getPlantDetail(plantId));
    }

    @Operation(summary = "대표 식물등록", security = {@SecurityRequirement(name = "bearer-key")})
    @PatchMapping("/star/{plantId}")
    public SingleResponse<String> makeStar(@PathVariable Long plantId) {
        return responseService.getSingleResponse(plantService.makeStar(plantId));
    }
}

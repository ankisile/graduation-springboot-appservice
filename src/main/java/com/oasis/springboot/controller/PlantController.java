package com.oasis.springboot.controller;

import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import com.oasis.springboot.dto.plant.PlantsResponseDto;
import com.oasis.springboot.response.ListResponse;
import com.oasis.springboot.response.ResponseService;
import com.oasis.springboot.response.SingleResponse;
import com.oasis.springboot.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Plant", description = "Plant 등록, 전체 Plants 조회 API -> 삭제는 일단 하지말아보샘")
@RequiredArgsConstructor
@RequestMapping("/api/plants")
@RestController
public class PlantController {
    private final PlantService plantService;
    private final ResponseService responseService;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping(value = "",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SingleResponse<String> savePlant(
            @Schema(name = "PlantSaveRequestDto",
                    description = "식물 저장(String이지만 json형식으로)",
                    required = true,
                    example = "{\n" +
                            "  \"name\": \"string\",\n" +
                            "  \"picture\": \"string\",\n" +
                            "  \"waterInterval\": 7,\n" +
                            "  \"nutritionInterval\": 90,\n" +
                            "  \"repottingInterval\": 90,\n" +
                            "  \"sunshine\": 5,\n" +
                            "  \"waterSupply\": 5,\n" +
                            "  \"highTemperature\": 23,\n" +
                            "  \"lowTemperature\": 25\n" +
                            "}"
            )
            @RequestPart("key") String requestDto,
            @Parameter(name = "file", description = "사진(maxSize: 10MB)")
            @RequestPart(value = "file", required = false) MultipartFile multipartFile ) {
        return responseService.getSingleResponse(plantService.savePlant(requestDto, multipartFile));
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

package com.oasis.springboot.controller;

import com.oasis.springboot.dto.plant.PlantDetailResponseDto;
import com.oasis.springboot.dto.plant.PlantsResponseDto;
import com.oasis.springboot.common.response.ListResponse;
import com.oasis.springboot.common.response.ResponseService;
import com.oasis.springboot.common.response.SingleResponse;
import com.oasis.springboot.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "Plant")
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
                            "  \"waterAlarmInterval\": 7,\n" +
                            "  \"waterSupply\": \"흙을 촉촉하게 유지함(물에 잠기지 않도록 주의)\",\n" +
                            "  \"sunshine\": 5,\n" +
                            "  \"highTemperature\": 23,\n" +
                            "  \"lowTemperature\": 25\n" +
                            "}"
            )
            @RequestPart("key") String requestDto,
            @Parameter(name = "file", description = "사진(maxSize: 10MB)")
            @RequestPart(value = "file") MultipartFile multipartFile ) {
        return responseService.getSingleResponse(plantService.savePlant(requestDto, multipartFile));
    }

    @Operation(summary = "get plants", description = "식물 조회 -> 홈화면, 식물일지 부분에서 쓰면 됨", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("")
    public ListResponse<PlantsResponseDto> getPlants(){
        return responseService.getListResponse(plantService.getPlants());
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @DeleteMapping("/{plantId}")
    public SingleResponse<String> deletePlant(@PathVariable Long plantId) {

        return responseService.getSingleResponse(plantService.deletePlant(plantId));
    }

    @Operation(description = "식물 디테일", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/{plantId}")
    public SingleResponse<PlantDetailResponseDto> getPlantDetail(@PathVariable Long plantId){
        return responseService.getSingleResponse(plantService.getPlantDetail(plantId));
    }
}

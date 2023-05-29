package com.oasis.springboot.controller;

import com.oasis.springboot.dto.KakaoPlaceResponseDto;
import com.oasis.springboot.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "식물샵 위치")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "식물샵", description = "500m이내 식물샵 가져오기")
    @Parameters({
            @Parameter(name = "x", description = "경도", example = "127"),
            @Parameter(name = "y", description = "위도", example = "36"),
    })
    @GetMapping("/places")
    private KakaoPlaceResponseDto getPlantStores(@RequestParam("x") String x, @RequestParam("y") String y){
        return placeService.getPlantStores(x, y);
    }
}

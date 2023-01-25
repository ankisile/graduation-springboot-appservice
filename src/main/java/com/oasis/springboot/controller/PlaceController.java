package com.oasis.springboot.controller;

import com.oasis.springboot.dto.KakaoPlaceResponseDto;
import com.oasis.springboot.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/places")
    private KakaoPlaceResponseDto getPlantStores(@RequestParam("x") String x, @RequestParam("y") String y){
        return placeService.getPlantStores(x, y);
    }
}

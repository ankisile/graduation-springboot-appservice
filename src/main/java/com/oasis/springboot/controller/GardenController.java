package com.oasis.springboot.controller;

import com.oasis.springboot.common.response.ListResponse;
import com.oasis.springboot.common.response.ResponseService;
import com.oasis.springboot.common.response.SingleResponse;
import com.oasis.springboot.dto.garden.GardenDetailResponseDto;
import com.oasis.springboot.dto.garden.GardenListResponseDto;
import com.oasis.springboot.service.GardenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/garden")
public class GardenController {

    private final GardenService gardenService;
    private final ResponseService responseService;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "전체 식물도감")
    @GetMapping()
    public ListResponse<GardenListResponseDto> getGardenList(){
        return responseService.getListResponse(gardenService.getGardenList());
    }


    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "식물도감 디테일")
    @GetMapping("/{gardenId}")
    public SingleResponse<GardenDetailResponseDto> getGardenDetail(@PathVariable Long gardenId){
        return responseService.getSingleResponse(gardenService.getDetailGarden(gardenId));
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "식물 도감 검색")
    @GetMapping("/search")
    public ListResponse<GardenListResponseDto> searchGarden(@RequestParam String keyword){
        return responseService.getListResponse(gardenService.searchGardenList(keyword));
    }

    //찜

}

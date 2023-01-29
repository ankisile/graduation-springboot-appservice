package com.oasis.springboot.controller;

import com.oasis.springboot.domain.calendar.CareType;
import com.oasis.springboot.dto.calendar.CalendarListResponseDto;
import com.oasis.springboot.response.ListResponse;
import com.oasis.springboot.response.ResponseService;
import com.oasis.springboot.response.SingleResponse;
import com.oasis.springboot.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "스케쥴")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/calendars")
public class CalendarController {

    private final CalendarService calendarService;
    private final ResponseService responseService;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @Parameter(name = "type", description = "물주기 = w 분갈이 = r 영양제 = n", example = "w")
    @PostMapping("/{plantId}")
    public SingleResponse<String> savePlantCare(@PathVariable Long plantId, @RequestParam("type") String type) {
        CareType careType = CareType.WATER;

        switch (type) {
            case "r":
                careType = CareType.REPOTTING;
                break;
            case "n":
                careType = CareType.NUTRITION;
                break;
        }

        return responseService.getSingleResponse(calendarService.savePlantCare(plantId, careType));
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("")
    public ListResponse<CalendarListResponseDto> getCalendar(){
        return responseService.getListResponse(calendarService.getCalendar());
    }
}

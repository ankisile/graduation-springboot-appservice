package com.oasis.springboot.controller;

import com.oasis.springboot.domain.calendar.CareType;
import com.oasis.springboot.dto.calendar.CalendarListResponseDto;
import com.oasis.springboot.response.ResponseService;
import com.oasis.springboot.response.SingleResponse;
import com.oasis.springboot.service.CalendarService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "스케쥴", description = "일단 이것도 하지 말아보샘")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/calendars")
public class CalendarController {

    private final CalendarService calendarService;
    private final ResponseService responseService;

    @Parameter(name = "type", description = "물주기 = w 분갈이 = r 영양제 = n", example = "w")
    @PostMapping("/{plantId}?{careType}")
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

    @GetMapping("")
    public List<CalendarListResponseDto> getCalendar(){
        return calendarService.getCalendar(1L);
    }
}

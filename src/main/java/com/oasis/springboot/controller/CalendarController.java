package com.oasis.springboot.controller;

import com.oasis.springboot.domain.calendar.CareType;
import com.oasis.springboot.dto.calendar.CalendarListResponseDto;
import com.oasis.springboot.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/calendars")
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping("/{plantId}?{careType}")
    public void savePlantCare(@PathVariable Long plantId, @RequestParam String type) {
        CareType careType = CareType.WATER;

        switch (type) {
            case "R":
                careType = CareType.REPOTTING;
                break;
            case "N":
                careType = CareType.NUTRITION;
                break;
        }

        calendarService.savePlantCare(1L, plantId, careType);
    }

    @GetMapping("")
    public List<CalendarListResponseDto> getCalendar(){
        return calendarService.getCalendar(1L);
    }
}

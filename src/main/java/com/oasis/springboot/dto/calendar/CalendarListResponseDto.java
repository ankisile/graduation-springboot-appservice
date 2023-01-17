package com.oasis.springboot.dto.calendar;

import com.oasis.springboot.domain.calendar.Calendar;
import com.oasis.springboot.domain.calendar.CareType;
import com.oasis.springboot.domain.plant.Plant;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class CalendarListResponseDto {
    private String date;
    private Plant plant;
    private CareType careType;

    public CalendarListResponseDto(Calendar entity) {
        this.date = entity.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.plant = entity.getPlant();
        this.careType = entity.getType();
    }
}

package com.oasis.springboot.dto.calendar;

import com.oasis.springboot.domain.calendar.Calendar;
import com.oasis.springboot.domain.calendar.CareType;
import com.oasis.springboot.domain.plant.Plant;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class CalendarListResponseDto {
    private String date;
    private String plantName;
    private CareType careType;

    public CalendarListResponseDto(Calendar entity) {
        this.date = entity.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.plantName = entity.getPlantName();
        this.careType = entity.getType();
    }
}

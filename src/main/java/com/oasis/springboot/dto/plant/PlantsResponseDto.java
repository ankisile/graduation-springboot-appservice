package com.oasis.springboot.dto.plant;

import com.oasis.springboot.domain.plant.Plant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
public class PlantsResponseDto {
    private Long id;
    private String name;
    private String picture;
    private Long DDay;
    private String recentRecordDate;
    private Boolean star;

    public PlantsResponseDto(Plant plant) {
        this.id = plant.getId();
        this.name = plant.getName();
        this.picture = plant.getPicture();
        this.DDay = ChronoUnit.DAYS.between(plant.getAdoptingDate(), LocalDateTime.now());
        this.recentRecordDate = plant.getRecentRecordDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.star = plant.getStar();
    }
}

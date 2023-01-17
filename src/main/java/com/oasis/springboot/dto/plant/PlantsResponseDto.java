package com.oasis.springboot.dto.plant;

import com.oasis.springboot.domain.plant.Plant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
public class PlantsResponseDto {
    private String name;
    private String picture;
    private Long DDay;
    private LocalDateTime modifiedDate;

    public PlantsResponseDto(Plant plant) {
        this.name = plant.getName();
        this.picture = plant.getPicture();
        this.DDay = ChronoUnit.DAYS.between(plant.getAdoptingDate(), LocalDateTime.now());
        this.modifiedDate = plant.getModifiedDate();
    }
}

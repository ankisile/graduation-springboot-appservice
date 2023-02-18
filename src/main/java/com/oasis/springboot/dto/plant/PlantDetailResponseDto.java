package com.oasis.springboot.dto.plant;

import com.oasis.springboot.domain.plant.Plant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PlantDetailResponseDto {
    private String name;
    private String picture;
    private LocalDate adoptingDate;
    private Integer waterAlarmInterval;
    private String waterSupply;
    private Double sunshine;
    private Integer highTemperature;
    private Integer lowTemperature;

    public PlantDetailResponseDto(Plant plant) {
        this.name = plant.getName();
        this.picture = plant.getPicture();
        this.adoptingDate = plant.getAdoptingDate();
        this.waterAlarmInterval = plant.getWaterAlarmInterval();
        this.waterSupply = plant.getWaterSupply();
        this.sunshine = plant.getSunshine();
        this.highTemperature = plant.getHighTemperature();
        this.lowTemperature = plant.getLowTemperature();
    }
}

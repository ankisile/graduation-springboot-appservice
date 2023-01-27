package com.oasis.springboot.dto.plant;

import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PlantSaveRequestDto {
    private String name;
    private String picture;
    private LocalDate adoptingDate;
    private Integer waterInterval;
    private Integer nutritionInterval;
    private Integer repottingInterval;
    private Double sunshine;
    private Double waterSupply;
    private Integer highTemperature;
    private Integer lowTemperature;

    @Builder
    public PlantSaveRequestDto(String name, String picture, Integer waterInterval, Integer nutritionInterval, Integer repottingInterval, Double sunshine, Double waterSupply, Integer highTemperature, Integer lowTemperature) {
        this.name = name;
        this.picture = picture;
        this.waterInterval = waterInterval;
        this.nutritionInterval = nutritionInterval;
        this.repottingInterval = repottingInterval;
        this.sunshine = sunshine;
        this.waterSupply = waterSupply;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
    }

    public Plant toEntity(User user){
        return Plant.builder()
                .name(name)
                .picture(picture)
                .adoptingDate(LocalDate.now())
                .waterInterval(waterInterval)
                .nutritionInterval(nutritionInterval)
                .repottingInterval(repottingInterval)
                .sunshine(sunshine)
                .waterSupply(waterSupply)
                .highTemperature(highTemperature)
                .lowTemperature(lowTemperature)
                .user(user)
                .build();
    }
}

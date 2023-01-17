package com.oasis.springboot.dto.plant;

import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PlantSaveRequestDto {
    private String name;
    private String picture;
    private LocalDateTime adoptingDate;
    private Integer waterInterval;
    private Integer nutritionInterval;
    private Integer repottingInterval;
    private Double sunshine;
    private Double waterSupply;
    private Integer highTemperature;
    private Integer lowTemperature;

    @Builder
    public PlantSaveRequestDto(String name, String picture, LocalDateTime adoptingDate, Integer waterInterval, Integer nutritionInterval, Integer repottingInterval, Double sunshine, Double waterSupply, Integer highTemperature, Integer lowTemperature) {
        this.name = name;
        this.picture = picture;
        this.adoptingDate = adoptingDate;
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
                .picture("ddd.jpg")
                .adoptingDate(LocalDateTime.now())
                .waterInterval(5)
                .nutritionInterval(90)
                .repottingInterval(90)
                .sunshine(3.5)
                .waterSupply(4.5)
                .highTemperature(25)
                .lowTemperature(30)
                .user(user)
                .build();
    }
}

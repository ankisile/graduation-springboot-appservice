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
    private Integer waterAlarmInterval;
    private String waterSupply;
    private Double sunshine;
    private Integer highTemperature;
    private Integer lowTemperature;

    @Builder
    public PlantSaveRequestDto(String name, String picture, Integer waterAlarmInterval,  String waterSupply,  Double sunshine,Integer highTemperature, Integer lowTemperature) {
        this.name = name;
        this.picture = picture;
        this.waterAlarmInterval = waterAlarmInterval;
        this.waterSupply = waterSupply;
        this.sunshine = sunshine;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
    }

    public Plant toEntity(User user){
        return Plant.builder()
                .name(name)
                .picture(picture)
                .adoptingDate(LocalDate.now())
                .waterAlarmInterval(waterAlarmInterval)
                .waterSupply(waterSupply)
                .sunshine(sunshine)
                .highTemperature(highTemperature)
                .lowTemperature(lowTemperature)
                .user(user)
                .build();
    }
}

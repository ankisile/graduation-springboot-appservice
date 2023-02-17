package com.oasis.springboot.dto.garden;

import com.oasis.springboot.domain.garden.Garden;
import lombok.Getter;

@Getter
public class GardenDetailResponseDto {
    private Long id;
    private String name;
    private String picture;
    private String temperature;
    private String humidity;
    private String adviceInfo;
    private String manageLevel;
    private String waterSupply;
    private String light;
    private String place;
    private String bug;

    public GardenDetailResponseDto(Garden garden) {
        this.id = garden.getId();
        this.name = garden.getName();
        this.picture = garden.getPicture();
        this.temperature = garden.getTemperature();
        this.humidity = garden.getHumidity();
        this.adviceInfo = garden.getAdviceInfo();
        this.manageLevel = garden.getManageLevel();
        this.waterSupply = garden.getWaterSupply();
        this.light = garden.getLight();
        this.place = garden.getPlace();
        this.bug = garden.getBug();
    }
}

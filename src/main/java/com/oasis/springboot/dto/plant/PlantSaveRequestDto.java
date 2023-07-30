package com.oasis.springboot.dto.plant;

import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class PlantSaveRequestDto {
    private String name;
    private String picture;
    private String adoptingDate;
    private String waterAlarmInterval;
    private String waterSupply;
    private String sunshine;
    private String highTemperature;
    private String lowTemperature;
    private MultipartFile file;

    @Builder
    public PlantSaveRequestDto(String name, String picture, String adoptingDate, String waterAlarmInterval, String waterSupply, String sunshine, String highTemperature, String lowTemperature, MultipartFile file) {
        this.name = name;
        this.picture = picture;
        this.adoptingDate = adoptingDate;
        this.waterAlarmInterval = waterAlarmInterval;
        this.waterSupply = waterSupply;
        this.sunshine = sunshine;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
        this.file = file;
    }

    public Plant toEntity(User user) {
        return Plant.builder()
                .name(name)
                .picture(picture)
                .adoptingDate(LocalDate.parse(adoptingDate, DateTimeFormatter.ISO_DATE))
                .waterAlarmInterval(Integer.parseInt(waterAlarmInterval))
                .waterSupply(waterSupply)
                .sunshine(Double.parseDouble(sunshine))
                .highTemperature(Integer.parseInt(highTemperature))
                .lowTemperature(Integer.parseInt(lowTemperature))
                .user(user)
                .build();
    }
}

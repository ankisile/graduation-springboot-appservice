package com.oasis.springboot.dto.garden;

import com.oasis.springboot.domain.garden.Garden;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GardenSaveDto {

    private String cntntsNo;
    private String name;
    private String picture; //rtnFileCours와 rtnStreFileNm의 마지막 값 합체
    private String temperature; //온도
    private String humidity;  //습도
    private String adviceInfo; //조언
    private String manageLevel; //관리 수준
    private String waterSupply; //물관리 봄
    private String light;
    private String place; // 배치장소
    private String bug; //병충해

    @Builder
    public GardenSaveDto(String cntntsNo, String name, String picture, String temperature, String humidity, String adviceInfo, String manageLevel, String waterSupply, String light, String place, String bug) {
        this.cntntsNo = cntntsNo;
        this.name = name;
        this.picture = picture;
        this.temperature = temperature;
        this.humidity = humidity;
        this.adviceInfo = adviceInfo;
        this.manageLevel = manageLevel;
        this.waterSupply = waterSupply;
        this.light = light;
        this.place = place;
        this.bug = bug;
    }

    public Garden toEntity(){
        return Garden.builder()
                .cntntsNo(cntntsNo)
                .name(name)
                .picture(picture)
                .temperature(temperature)
                .humidity(humidity)
                .adviceInfo(adviceInfo)
                .manageLevel(manageLevel)
                .waterSupply(waterSupply)
                .light(light)
                .place(place)
                .bug(bug)
                .build();
    }

}

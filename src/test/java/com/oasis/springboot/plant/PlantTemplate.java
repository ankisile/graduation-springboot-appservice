package com.oasis.springboot.plant;

import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;

public class PlantTemplate {
    public static final String NAME = "PLANTname";
    public static final String NAME2 = "PLANTname2";
    public static final String ADOPTINGDATE = "2023-03-03";
    public static final String WATERALARMINTERVAL = "2";
    public static final String WATERSUPPLY = "많음";
    public static final String SUNSHINE = "3.0";
    public static final String HIGHTEMPERATURE = "40";
    public static final String LOWTEMPERATURE = "-10";

    public static PlantSaveRequestDto makePlantSaveRequestDtoWithFile() throws Exception {
        return new PlantSaveRequestDto(NAME, null, ADOPTINGDATE, WATERALARMINTERVAL, WATERSUPPLY, SUNSHINE, HIGHTEMPERATURE, LOWTEMPERATURE, getPlantImage());
    }

    public static PlantSaveRequestDto makePlantSaveRequestDtoWithUrl() throws Exception {
        return new PlantSaveRequestDto(NAME, "testImage.jpg", ADOPTINGDATE, WATERALARMINTERVAL, WATERSUPPLY, SUNSHINE, HIGHTEMPERATURE, LOWTEMPERATURE, null);
    }

    public static PlantSaveRequestDto makeTestPlant2() throws Exception {
        return new PlantSaveRequestDto(NAME2, null, ADOPTINGDATE, WATERALARMINTERVAL, WATERSUPPLY, SUNSHINE, HIGHTEMPERATURE, LOWTEMPERATURE, getPlantImage());
    }

    public static MockMultipartFile getPlantImage() throws Exception {
        String fileName = "testImage";
        String contentType = "jpg";
        String filePath = "src/test/resources/images/" + fileName + "." + contentType;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return new MockMultipartFile("plantImage", fileName + "." + contentType, contentType, fileInputStream);
    }
}

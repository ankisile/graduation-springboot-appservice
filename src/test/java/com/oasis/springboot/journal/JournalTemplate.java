package com.oasis.springboot.journal;

import com.oasis.springboot.dto.journal.JournalSaveRequestDto;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;

public class JournalTemplate {
    public static final String NAME = "PLANTname";
    public static final String ADOPTINGDATE = "2023-03-03";
    public static final String WATERALARMINTERVAL = "2";
    public static final String WATERSUPPLY = "많음";
    public static final String SUNSHINE = "3.0";
    public static final String HIGHTEMPERATURE = "40";
    public static final String LOWTEMPERATURE = "-10";
    public static final String CONTENT = "내용";

    public static PlantSaveRequestDto makePlantSaveRequestDtoWithFile() throws Exception {
        return new PlantSaveRequestDto(NAME, null, ADOPTINGDATE, WATERALARMINTERVAL, WATERSUPPLY, SUNSHINE, HIGHTEMPERATURE, LOWTEMPERATURE, getPlantImage());
    }
    public static JournalSaveRequestDto makeJournalSaveRequestDtoWithFile() throws Exception {
        return new JournalSaveRequestDto( CONTENT, getJournalImage(), "true");
    }

    public static JournalSaveRequestDto makeJournalSaveRequestDtoWithoutFile() throws Exception {
        return new JournalSaveRequestDto( CONTENT, null, "false");
    }

    public static MockMultipartFile getJournalImage() throws Exception {
        String fileName = "testImage";
        String contentType = "jpg";
        String filePath = "src/test/resources/images/" + fileName + "." + contentType;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return new MockMultipartFile("journalImage", fileName + "." + contentType, contentType, fileInputStream);
    }

    public static MockMultipartFile getPlantImage() throws Exception {
        String fileName = "testImage";
        String contentType = "jpg";
        String filePath = "src/test/resources/images/" + fileName + "." + contentType;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return new MockMultipartFile("plantImage", fileName + "." + contentType, contentType, fileInputStream);
    }
}

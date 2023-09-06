package com.oasis.springboot.journal;

import com.oasis.springboot.dto.journal.JournalSaveRequestDto;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;

public class JournalTemplate {

    public static final String CONTENT1 = "내용1";

    public static final String CONTENT2 = "내용2";


    public static JournalSaveRequestDto makeJournalSaveRequestDtoWithFile() throws Exception {
        return new JournalSaveRequestDto( CONTENT1, getJournalImage(), "true");
    }

    public static JournalSaveRequestDto makeJournalSaveRequestDtoWithoutFile() throws Exception {
        return new JournalSaveRequestDto( CONTENT2, null, "false");
    }

    public static MockMultipartFile getJournalImage() throws Exception {
        String fileName = "testImage";
        String contentType = "jpg";
        String filePath = "src/test/resources/images/" + fileName + "." + contentType;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return new MockMultipartFile("journalImage", fileName + "." + contentType, contentType, fileInputStream);
    }

}

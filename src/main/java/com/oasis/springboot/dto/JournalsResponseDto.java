package com.oasis.springboot.dto;

import com.oasis.springboot.domain.journal.Journal;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class JournalsResponseDto {
    private String content;
    private String picture;
    private String date;

    @Builder
    public JournalsResponseDto(Journal entity) {
        this.content = entity.getContent();
        this.picture = entity.getPicture();
        this.date = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}

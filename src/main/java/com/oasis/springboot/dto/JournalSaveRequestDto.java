package com.oasis.springboot.dto;

import com.oasis.springboot.domain.journal.Journal;
import com.oasis.springboot.domain.plant.Plant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JournalSaveRequestDto {
    private String content;
    private String picture;

    @Builder
    public JournalSaveRequestDto(String content, String picture) {
        //picture 수정 필요
        this.content = content;
        this.picture = picture;
    }

    public Journal toEntity(Plant plant){
        return Journal.builder()
                .content(content)
                .picture(picture)
                .plant(plant)
                .build();
    }
}

package com.oasis.springboot.dto.journal;

import com.oasis.springboot.domain.journal.Journal;
import com.oasis.springboot.domain.plant.Plant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JournalSaveRequestDto {
    private String content;
    private String picture;

    @Builder
    public JournalSaveRequestDto(String content, String picture) {
        this.content = content;
    }

    public Journal toEntity(Plant plant){
        return Journal.builder()
                .content(content)
                .picture(picture)
                .plant(plant)
                .build();
    }
}

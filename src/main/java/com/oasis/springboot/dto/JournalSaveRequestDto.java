package com.oasis.springboot.dto;

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
        this.content = content;
        this.picture = picture;
    }
}

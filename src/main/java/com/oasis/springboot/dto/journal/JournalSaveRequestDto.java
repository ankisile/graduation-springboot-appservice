package com.oasis.springboot.dto.journal;

import com.oasis.springboot.domain.journal.Journal;
import com.oasis.springboot.domain.plant.Plant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class JournalSaveRequestDto {
    @NotBlank(message = "내용이 없습니다.")
    private String content;
    private MultipartFile file;
    private String isFileExist;

    @Builder
    public JournalSaveRequestDto(String content, MultipartFile file, String isFileExist) {
        this.content = content;
        this.file = file;
        this.isFileExist = isFileExist;
    }

    public Journal toEntity(Plant plant, String picture) {
        return Journal.builder()
                .content(content)
                .picture(picture)
                .plant(plant)
                .build();
    }
}

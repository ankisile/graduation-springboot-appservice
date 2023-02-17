package com.oasis.springboot.dto.garden;

import com.oasis.springboot.domain.garden.Garden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GardenListResponseDto {
    private Long id;
    private String name;
    private String picture;
    @Schema(description = "난이도(초보자/ 경험자/ 전문가/ 빈칸 이 4개 중 하나 -> 별로 치환)")
    private String manageLevel;

    @Builder
    public GardenListResponseDto(Garden garden) {
        this.id = garden.getId();
        this.name = garden.getName();
        this.picture = garden.getPicture();
        this.manageLevel = garden.getManageLevel();
    }
}

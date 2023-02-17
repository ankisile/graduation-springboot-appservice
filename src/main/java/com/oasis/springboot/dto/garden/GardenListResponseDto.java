package com.oasis.springboot.dto.garden;

import com.oasis.springboot.domain.garden.Garden;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GardenListResponseDto {
    private Long id;
    private String name;
    private String picture;
    private String manageLevel;

    @Builder
    public GardenListResponseDto(Garden garden) {
        this.id = garden.getId();
        this.name = garden.getName();
        this.picture = garden.getPicture();
        this.manageLevel = garden.getManageLevel();
    }
}

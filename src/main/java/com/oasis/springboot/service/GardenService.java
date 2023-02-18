package com.oasis.springboot.service;

import com.oasis.springboot.common.exception.InvalidateGardenException;
import com.oasis.springboot.domain.bookmark.BookmarkRepository;
import com.oasis.springboot.domain.garden.GardenRepository;
import com.oasis.springboot.dto.garden.GardenDetailResponseDto;
import com.oasis.springboot.dto.garden.GardenListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GardenService {

    private final GardenRepository gardenRepository;
    private final BookmarkService bookmarkService;

    public List<GardenListResponseDto> getGardenList(){
        return gardenRepository.findAll()
                .stream()
                .map(GardenListResponseDto::new)
                .collect(Collectors.toList());
    }

    public GardenDetailResponseDto getDetailGarden(Long id){
        boolean isBookmark = bookmarkService.isBookmark(id);
        return gardenRepository.findById(id)
                .map(garden -> new GardenDetailResponseDto(garden, isBookmark))
                .orElseThrow(InvalidateGardenException::new);
    }

    public List<GardenListResponseDto> searchGardenList(String keyword){
        return gardenRepository.findByNameContaining(keyword)
                .stream()
                .map(GardenListResponseDto::new)
                .collect(Collectors.toList());
    }
}

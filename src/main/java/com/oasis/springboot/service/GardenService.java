package com.oasis.springboot.service;

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

    public List<GardenListResponseDto> getGardenList(){
        return gardenRepository.findAll()
                .stream()
                .map(GardenListResponseDto::new)
                .collect(Collectors.toList());
    }

    public GardenDetailResponseDto getDetailGarden(Long id){
        return gardenRepository.findById(id)
                .map(garden -> new GardenDetailResponseDto(garden))
                .orElseThrow(() -> new RuntimeException("존재하지 않는 garden 입니다. id=" + id));
    }

    public List<GardenListResponseDto> searchGardenList(String keyword){
        return gardenRepository.findByNameContaining(keyword)
                .stream()
                .map(GardenListResponseDto::new)
                .collect(Collectors.toList());
    }


    //찜
}

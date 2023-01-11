package com.oasis.springboot.service;

import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.plant.PlantRepository;
import com.oasis.springboot.domain.user.Role;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.dto.PlantSaveRequestDto;
import com.oasis.springboot.dto.PlantsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;

    @Transactional
    public void savePlant(PlantSaveRequestDto requestDto) {
        //user 관련 정보 jwt에서 가져오는 과정 필요
        User user = User.builder()
                .email("aaa@gmail.com")
                .name("aaa")
                .picture("aaa")
                .role(Role.USER)
                .build();
        plantRepository.save(requestDto.toEntity(user));
    }

    public List<PlantsResponseDto> getPlants(Long userId) {
        //user관련 정보 jwt에서 가져오는 과정 필요 + parameter 수정 필요 + 최근 수정 날짜 수정 필요
        return plantRepository.findByUser_Id(userId)
                .stream()
                .map(PlantsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePlant(Long plantId){
        //일지 삭제도 필요
        Plant plant = plantRepository.findById(plantId)
                        .orElseThrow(()->new IllegalArgumentException("해당 식물이 없습니다. id = "+ plantId));
        plantRepository.delete(plant);
    }
}

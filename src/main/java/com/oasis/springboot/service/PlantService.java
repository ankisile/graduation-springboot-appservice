package com.oasis.springboot.service;

import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.plant.PlantRepository;
import com.oasis.springboot.domain.user.Role;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import com.oasis.springboot.dto.plant.PlantsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final UserService userService;

    @Transactional
    public String savePlant(PlantSaveRequestDto requestDto) {
        User user = userService.findByEmail();
        System.out.print(user);

        plantRepository.save(requestDto.toEntity(user));
        //adopting date 도 넣어야 됨
        return "식물 등록 성공";
    }

    //유저가 같이 딸려서 n+1 문제 발생 흑흑  -> fetch join으로 할 수 있을거 같기도 함 한번 해봐야 될듯
    public List<PlantsResponseDto> getPlants() {
        Long userId = userService.findUserId();
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

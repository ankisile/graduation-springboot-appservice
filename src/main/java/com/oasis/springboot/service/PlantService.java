package com.oasis.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.springboot.domain.calendar.Calendar;
import com.oasis.springboot.domain.calendar.CalendarRepository;
import com.oasis.springboot.domain.calendar.CareType;
import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.plant.PlantRepository;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import com.oasis.springboot.dto.plant.PlantsResponseDto;
import com.oasis.springboot.handler.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final CalendarRepository calendarRepository;
    private final UserService userService;
    private final S3Uploader s3Uploader;

    @Transactional
    public String savePlant(String dto, MultipartFile file) {

        ObjectMapper mapper = new ObjectMapper();
        PlantSaveRequestDto requestDto = null;
        try {
            requestDto = mapper.readValue(dto, PlantSaveRequestDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if(file!=null) {
                String s3Url = s3Uploader.upload(file, "static");
                requestDto.setPicture(s3Url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = userService.findByEmail();
        System.out.print(user);

        Plant plant = requestDto.toEntity(user);
        plantRepository.save(plant);

        Calendar calendar = Calendar.builder()
                .type(CareType.ADOPTING)
                .plantName(requestDto.getName())
                .user(user)
                .build();
        calendarRepository.save(calendar);

        return "식물 등록 성공";
    }

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

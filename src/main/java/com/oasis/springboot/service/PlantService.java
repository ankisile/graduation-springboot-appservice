package com.oasis.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.springboot.common.exception.InvalidatePlantException;
import com.oasis.springboot.domain.calendar.Calendar;
import com.oasis.springboot.domain.calendar.CalendarRepository;
import com.oasis.springboot.domain.calendar.CareType;
import com.oasis.springboot.domain.journal.Journal;
import com.oasis.springboot.domain.journal.JournalRepository;
import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.plant.PlantRepository;
import com.oasis.springboot.domain.pushAlarm.PushAlarm;
import com.oasis.springboot.domain.pushAlarm.PushAlarmRepository;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.dto.plant.PlantDetailResponseDto;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import com.oasis.springboot.dto.plant.PlantsResponseDto;
import com.oasis.springboot.common.handler.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final CalendarRepository calendarRepository;
    private final JournalRepository journalRepository;
    private final PushAlarmRepository pushAlarmRepository;
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
                String s3Url = s3Uploader.upload(file, "plant");
                requestDto.setPicture(s3Url.substring(62));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = userService.findUser();
        System.out.print(user);

        Plant plant = requestDto.toEntity(user);
        plantRepository.save(plant);

        Calendar calendar = Calendar.builder()
                .type(CareType.ADOPTING)
                .plantName(requestDto.getName())
                .user(user)
                .plant(plant)
                .build();
        calendarRepository.save(calendar);

        PushAlarm pushAlarm = PushAlarm.builder()
                .date(LocalDate.now().plusDays(requestDto.getWaterAlarmInterval()))
                .plant(plant)
                .user(user)
                .build();
        pushAlarmRepository.save(pushAlarm);

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
    public String deletePlant(Long plantId){
        List<Journal> journalList = journalRepository.findJournalsByPlantIdFetchJoin(plantId);

        for(Journal journal : journalList){
            if(journal.getPicture() != null) {
                String str = journal.getPicture();
                String path = str.substring(62, str.length());
                s3Uploader.delete(path);
            }
            journalRepository.delete(journal);
        }

        List<Calendar> calendarList = calendarRepository.findAllByPlantId(plantId);

        for(Calendar calendar : calendarList){
            calendarRepository.delete(calendar);
        }

        Plant plant = plantRepository.findById(plantId)
                        .orElseThrow(InvalidatePlantException::new);

        plantRepository.delete(plant);

        List<PushAlarm> pushAlarmList = pushAlarmRepository.findAllByPlantId(plantId);
        for(PushAlarm pushAlarm : pushAlarmList){
            pushAlarmRepository.delete(pushAlarm);
        }

        return "식물 삭제 성공";
    }

    public PlantDetailResponseDto getPlantDetail(Long plantId){
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(InvalidatePlantException::new);
        return new PlantDetailResponseDto(plant);
    }

    //식물 수정

}

package com.oasis.springboot.service;

import com.oasis.springboot.common.exception.InvalidatePlantException;
import com.oasis.springboot.common.handler.S3Uploader;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final CalendarRepository calendarRepository;
    private final JournalRepository journalRepository;
    private final PushAlarmRepository pushAlarmRepository;
    private final UserService userService;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long savePlant(PlantSaveRequestDto requestDto) {

        String s3Url = requestDto.getPicture();
        if (s3Url == null) {
            s3Url = s3Uploader.getFileS3Url(requestDto.getFile(), "plant");
            requestDto.setPicture(s3Url);
        }

        User user = userService.findUser();

        Plant plant = requestDto.toEntity(user);
        plantRepository.save(plant);

        Calendar calendar = Calendar.builder()
                .type(CareType.ADOPTING)
                .plantName(plant.getName())
                .user(user)
                .plant(plant)
                .build();
        calendarRepository.save(calendar);

        PushAlarm pushAlarm = PushAlarm.builder()
                .date(plant.getAdoptingDate().plusDays(plant.getWaterAlarmInterval()))
                .plant(plant)
                .user(user)
                .build();
        pushAlarmRepository.save(pushAlarm);

        return plant.getId();
    }

    public List<PlantsResponseDto> getPlants() {
        Long userId = userService.findUserId();
        return plantRepository.findByUser_Id(userId)
                .stream()
                .map(PlantsResponseDto::new)
                .collect(Collectors.toList());
    }

    public PlantDetailResponseDto getPlantDetail(Long plantId) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(InvalidatePlantException::new);
        return new PlantDetailResponseDto(plant);
    }

    @Transactional
    public String deletePlant(Long plantId) {
        List<Journal> journalList = journalRepository.findJournalsByPlantIdFetchJoin(plantId);

        for (Journal journal : journalList) {
            if (journal.getPicture() != null) {
                String str = journal.getPicture();
                String path = str.substring(62, str.length());
                s3Uploader.delete(path);
            }
            journalRepository.delete(journal);
        }

        List<Calendar> calendarList = calendarRepository.findAllByPlantId(plantId);

        for (Calendar calendar : calendarList) {
            calendarRepository.delete(calendar);
        }

        List<PushAlarm> pushAlarmList = pushAlarmRepository.findAllByPlantId(plantId);
        for (PushAlarm pushAlarm : pushAlarmList) {
            pushAlarmRepository.delete(pushAlarm);
        }

        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(InvalidatePlantException::new);

        plantRepository.delete(plant);

        return "식물 삭제 성공";
    }

    @Transactional
    public String makeStar(Long plantId) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(InvalidatePlantException::new);
        if (plant.getStar()) {
            plant.updatePlantStar(false);
            return "대표 식물 취소 완료";
        } else {
            plant.updatePlantStar(true);
            return "대표 식물 성공 완료";
        }
    }

}

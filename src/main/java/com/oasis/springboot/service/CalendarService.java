package com.oasis.springboot.service;

import com.oasis.springboot.domain.calendar.Calendar;
import com.oasis.springboot.domain.calendar.CalendarRepository;
import com.oasis.springboot.domain.calendar.CareType;
import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.plant.PlantRepository;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.dto.calendar.CalendarListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final PlantRepository plantRepository;
    private final UserService userService;

    public String savePlantCare(Long plantId, CareType careType) {
        User user = userService.findByEmail();
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(()->new IllegalArgumentException("해당 식물이 없습니다. id = "+ plantId));

        Calendar calendar = Calendar.builder()
                .type(careType)
                .plant(plant)
                .user(user)
                .build();

        calendarRepository.save(calendar);

        return "등록 성공";
    }

    //앱의 속도를 빠르게 하기 위해서 어떻게 해야할까나 -> 일단은 user를 가지고 가져오기
    public List<CalendarListResponseDto> getCalendar(Long userId) {
        //이것도 유저 정보가져와서 userId로 할 수 있을듯 + N+1 문제 해결 필요
        return calendarRepository.findByUser_Id(userId)
                .stream()
                .map(CalendarListResponseDto::new)
                .collect(Collectors.toList());

    }
}

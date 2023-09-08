package com.oasis.springboot.calendar;

import com.oasis.springboot.domain.calendar.Calendar;
import com.oasis.springboot.domain.calendar.CalendarRepository;
import com.oasis.springboot.domain.calendar.CareType;
import com.oasis.springboot.domain.plant.PlantRepository;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.calendar.CalendarListResponseDto;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import com.oasis.springboot.service.CalendarService;
import com.oasis.springboot.service.PlantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.oasis.springboot.plant.PlantTemplate.makePlantSaveRequestDtoWithFile;
import static com.oasis.springboot.user.UserTemplate.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@WithMockUser(username = EMAIL, password = PASSWORD)
public class CalendarServiceTest {

    @Autowired
    CalendarService calendarService;

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    PlantService plantService;

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    public void 캘린더_가져오기() throws Exception {
        User user = saveUser();
        PlantSaveRequestDto saveRequestDto = makePlantSaveRequestDtoWithFile();

        Long plantId = plantService.savePlant(saveRequestDto);

        calendarService.savePlantCare(plantId, CareType.NUTRITION);

        List<CalendarListResponseDto> calendarListResponseDtoList = calendarService.getCalendar();

        String calendarPlantName = calendarListResponseDtoList.get(0).getPlantName();
        String plantName = plantRepository.findById(plantId).get().getName();

        assertThat(calendarPlantName).isEqualTo(plantName);


    }

    private User saveUser() throws Exception {
        User user = makeTestUser();
        userRepository.save(user);
        return user;
    }
}

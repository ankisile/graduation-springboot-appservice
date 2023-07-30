package com.oasis.springboot.plant;

import com.oasis.springboot.common.exception.InvalidatePlantException;
import com.oasis.springboot.common.handler.S3Uploader;
import com.oasis.springboot.domain.calendar.Calendar;
import com.oasis.springboot.domain.calendar.CalendarRepository;
import com.oasis.springboot.domain.calendar.CareType;
import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.plant.PlantRepository;
import com.oasis.springboot.domain.pushAlarm.PushAlarm;
import com.oasis.springboot.domain.pushAlarm.PushAlarmRepository;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.plant.PlantDetailResponseDto;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import com.oasis.springboot.dto.plant.PlantsResponseDto;
import com.oasis.springboot.service.PlantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.oasis.springboot.plant.PlantTemplate.*;
import static com.oasis.springboot.user.UserTemplate.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@WithMockUser(username = EMAIL, password = PASSWORD)
public class PlantServiceTest {
    @Autowired
    PlantService plantService;

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    PushAlarmRepository pushAlarmRepository;

    @Autowired
    S3Uploader s3Uploader;

    @Test
    void 식물_등록하기_파일과() throws Exception {
        //given
        User user = saveUser();
        PlantSaveRequestDto saveRequestDto = makePlantSaveRequestDtoWithFile();

        //when
        Long plantId = plantService.savePlant(saveRequestDto);

        //then
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new InvalidatePlantException());
        List<Calendar> calendarList = calendarRepository.findAllByPlantId(plantId);
        List<PushAlarm> pushAlarmList = pushAlarmRepository.findAllByPlantId(plantId);

        assertThat(plant.getName()).isEqualTo(saveRequestDto.getName());
        assertThat(plant.getSunshine()).isEqualTo(Double.parseDouble(saveRequestDto.getSunshine()));
        assertThat(plant.getPicture()).isNotNull();
        assertThat(calendarList.get(0).getType()).isEqualTo(CareType.ADOPTING);
        assertThat(pushAlarmList.get(0).getDate()).isEqualTo(LocalDate.parse(saveRequestDto.getAdoptingDate()).plusDays(Integer.parseInt(saveRequestDto.getWaterAlarmInterval())));

        //teardown
        String str = plant.getPicture();
        String path = str.substring(62, str.length());
        s3Uploader.delete(path);
    }

    @Test
    void 식물_등록하기_url과() throws Exception {
        //given
        User user = saveUser();
        PlantSaveRequestDto saveRequestDto = makePlantSaveRequestDtoWithUrl();

        //when
        Long plantId = plantService.savePlant(saveRequestDto);

        //then
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new InvalidatePlantException());
        List<Calendar> calendarList = calendarRepository.findAllByPlantId(plantId);
        List<PushAlarm> pushAlarmList = pushAlarmRepository.findAllByPlantId(plantId);

        assertThat(plant.getName()).isEqualTo(saveRequestDto.getName());
        assertThat(plant.getSunshine()).isEqualTo(Double.parseDouble(saveRequestDto.getSunshine()));
        assertThat(plant.getPicture()).isEqualTo(saveRequestDto.getPicture());
        assertThat(calendarList.get(0).getType()).isEqualTo(CareType.ADOPTING);
        assertThat(pushAlarmList.get(0).getDate()).isEqualTo(LocalDate.parse(saveRequestDto.getAdoptingDate()).plusDays(Integer.parseInt(saveRequestDto.getWaterAlarmInterval())));
    }

    @Test
    void 식물_모두_가져오기() throws Exception {
        User user = saveUser();
        PlantSaveRequestDto requestDto1 = makePlantSaveRequestDtoWithUrl();
        PlantSaveRequestDto requestDto2 = makeTestPlant2();

        Long plantId1 = plantService.savePlant(requestDto1);
        Long plantId2 = plantService.savePlant(requestDto2);

        List<PlantsResponseDto> plantsResponseDtoList = plantService.getPlants();

        assertThat(plantsResponseDtoList.size()).isEqualTo(2);
        assertThat(plantsResponseDtoList.get(0).getId()).isEqualTo(plantId1);
        assertThat(plantsResponseDtoList.get(1).getId()).isEqualTo(plantId2);

    }

    @Test
    void 식물_디테일_가져오기() throws Exception {
        User user = saveUser();
        PlantSaveRequestDto requestDto = makePlantSaveRequestDtoWithUrl();
        Long plantId = plantService.savePlant(requestDto);

        PlantDetailResponseDto responseDto = plantService.getPlantDetail(plantId);

        assertThat(responseDto.getName()).isEqualTo(responseDto.getName());
        assertThat(requestDto.getWaterAlarmInterval()).isEqualTo(requestDto.getWaterAlarmInterval());
    }

    @Test
    void 식물_삭제() throws Exception {
        User user = saveUser();
        PlantSaveRequestDto requestDto = makePlantSaveRequestDtoWithUrl();
        Long plantId = plantService.savePlant(requestDto);

        plantService.deletePlant(plantId);

        Optional<Plant> plantOptional = plantRepository.findById(plantId);
        assertThat(plantOptional).isEmpty();


    }

    @Test
    void 대표_식물_만들기() throws Exception {
        User user = saveUser();
        PlantSaveRequestDto requestDto = makePlantSaveRequestDtoWithUrl();
        Long plantId = plantService.savePlant(requestDto);

        plantService.makeStar(plantId);

        Optional<Plant> plantOptional = plantRepository.findById(plantId);

        assertThat(plantOptional.get().getStar()).isTrue();
    }

    private User saveUser() throws Exception {
        User user = makeTestUser();
        userRepository.save(user);
        return user;
    }
}

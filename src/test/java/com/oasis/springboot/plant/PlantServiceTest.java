package com.oasis.springboot.plant;

import com.oasis.springboot.common.exception.InvalidatePlantException;
import com.oasis.springboot.common.handler.S3Uploader;
import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.plant.PlantRepository;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import com.oasis.springboot.service.PlantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static com.oasis.springboot.plant.PlantTemplate.makePlantSaveRequestDtoWithFile;
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
    S3Uploader s3Uploader;

    @Test
    void 식물_등록하기_파일과() throws Exception {
        //given
        saveUser();
        PlantSaveRequestDto saveRequestDto = makePlantSaveRequestDtoWithFile();

        //when
        Long plantId = plantService.savePlant(saveRequestDto);
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new InvalidatePlantException());

        //then
        assertThat(plant.getName()).isEqualTo(saveRequestDto.getName());
        assertThat(plant.getSunshine()).isEqualTo(saveRequestDto.getSunshine());
        assertThat(plant.getPicture()).isNotNull();

        //teardown
        String str = plant.getPicture();
        String path = str.substring(62, str.length());
        s3Uploader.delete(path);
    }

    @Test
    void 식물_등록하기_url과() {

    }

    void 식물_디테일_가져오기() {

    }

    void 대표_식물_만들기() {
    }

    private void saveUser() throws Exception {
        User user = makeTestUser();
        userRepository.save(user);
    }
}

package com.oasis.springboot.garden;

import com.oasis.springboot.domain.garden.Garden;
import com.oasis.springboot.domain.garden.GardenRepository;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.garden.GardenDetailResponseDto;
import com.oasis.springboot.dto.garden.GardenListResponseDto;
import com.oasis.springboot.service.GardenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.oasis.springboot.user.UserTemplate.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@WithMockUser(username = EMAIL, password = PASSWORD)
public class GardenServiceTest {

    @Autowired
    GardenService gardenService;

    @Autowired
    GardenRepository gardenRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void 식물도감_리스트() {
        List<GardenListResponseDto> gardenList = gardenService.getGardenList();
        Long id = gardenList.get(0).getId();
        String name = gardenList.get(0).getName();
        Garden garden = gardenRepository.findById(id).get();
        assertThat(name).isEqualTo(garden.getName());
    }

    @Test
    void 식물도감_상세() throws Exception {
        User user = saveUser();

        List<GardenListResponseDto> gardenList = gardenService.getGardenList();
        Long id = gardenList.get(0).getId();

        GardenDetailResponseDto gardenDetailResponseDto = gardenService.getDetailGarden(id);
        String name = gardenDetailResponseDto.getName();
        Garden garden = gardenRepository.findById(id).get();

        assertThat(name).isEqualTo(garden.getName());

    }

    void 식물도감_검색() {


    }

    private User saveUser() throws Exception {
        User user = makeTestUser();
        userRepository.save(user);
        return user;
    }
}

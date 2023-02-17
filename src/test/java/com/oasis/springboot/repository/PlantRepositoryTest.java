//package com.oasis.springboot.repository;
//
//import com.oasis.springboot.domain.plant.Plant;
//import com.oasis.springboot.domain.plant.PlantRepository;
//import com.oasis.springboot.domain.user.Role;
//import com.oasis.springboot.domain.user.User;
//import com.oasis.springboot.domain.user.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class PlantRepositoryTest {
//
//    @Autowired
//    PlantRepository plantRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @BeforeEach
//    public void init() {
//        User user = User.builder()
//                .email("aaa@gmail.com")
//                .password("1111")
//                .name("aaa")
//                .picture("aaa")
//                .role(Role.USER)
//                .build();
//        userRepository.save(user);
//
//        Plant plant = Plant.builder()
//                .name("plantName")
//                .picture("ddd.jpg")
//                .adoptingDate(LocalDate.now())
//                .waterAlarmInterval(5)
//                .waterSupply("흙을 촉촉하게 유지함(물에 잠기지 않도록 주의)")
//                .sunshine(3.5)
//                .highTemperature(25)
//                .lowTemperature(30)
//                .user(user)
//                .build();
//        plantRepository.save(plant);
//
//    }
//
//
//    @Test
//    public void testPlantFindByUserId() {
//        //given
//        String name = "plantName";
//        Long userId = userRepository.findAll().get(0).getId();
//
//        //when
//        List<Plant> plantList = plantRepository.findByUser_Id(userId);
//
//        //then
//        Plant plant1 = plantList.get(0);
//        assertThat(plant1.getName()).isEqualTo(name);
//        assertThat(plant1.getCreatedDate()).isBefore(LocalDateTime.now());
//
//    }
//
//
//}

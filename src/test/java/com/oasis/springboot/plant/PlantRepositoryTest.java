//package com.oasis.springboot.plant;
//
//import com.oasis.springboot.domain.plant.Plant;
//import com.oasis.springboot.domain.plant.PlantRepository;
//import com.oasis.springboot.domain.user.Role;
//import com.oasis.springboot.domain.user.User;
//import com.oasis.springboot.domain.user.UserRepository;
//import org.junit.jupiter.api.AfterEach;
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
//        String name = "plantName";
//        Plant plant = Plant.builder()
//                .name(name)
//                .picture("ddd.jpg")
//                .adoptingDate(LocalDate.now())
//                .waterInterval(5)
//                .nutritionInterval(90)
//                .repottingInterval(90)
//                .sunshine(3.5)
//                .waterSupply(4.5)
//                .highTemperature(25)
//                .lowTemperature(30)
//                .user(user)
//                .build();
//        plantRepository.save(plant);
//    }
//
//    @AfterEach
//    public void cleanUp() {
//        plantRepository.deleteAll();
//        userRepository.deleteAll();
//    }
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
////    @Test
////    public void testPlantDelete(){
////        //given
////        Long userId = userRepository.findAll().get(0).getId();
////        List<Plant> plantList = plantRepository.findByUser_Id(userId);
////        Plant deletePlant = plantList.get(0);
////
////        //when
////        plantRepository.delete(deletePlant);
////        plantList =  plantRepository.findByUser_Id(userId);
////
////        //then
////        assertThat(plantList.size()).isEqualTo(0);
////    }
//
//
//}

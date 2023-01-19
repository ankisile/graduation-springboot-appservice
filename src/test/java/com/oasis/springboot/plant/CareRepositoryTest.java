//package com.oasis.springboot.plant;
//
//import com.oasis.springboot.domain.calendar.Calendar;
//import com.oasis.springboot.domain.calendar.CalendarRepository;
//import com.oasis.springboot.domain.calendar.CareType;
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
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class CareRepositoryTest {
//    @Autowired
//    CalendarRepository calendarRepository;
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
//
//        Calendar calendar = Calendar.builder()
//                .type(CareType.WATER)
//                .plant(plant)
//                .user(user)
//                .build();
//        calendarRepository.save(calendar);
//    }
//
//    @AfterEach
//    public void cleanUp() {
//        calendarRepository.deleteAllInBatch();
//        plantRepository.deleteAllInBatch();
//        userRepository.deleteAll();
//    }
//
//    @Test
//    public void testCalendarFindByUserId() {
//        Long userId = userRepository.findAll().get(0).getId();
//
//        List<Calendar> calendarList = calendarRepository.findByUser_Id(userId);
//
//        Calendar calendar = calendarList.get(0);
//        assertThat(calendar.getDate()).isEqualTo(LocalDate.now());
//        assertThat(calendar.getType()).isEqualTo(CareType.WATER);
//
//
//    }
//}

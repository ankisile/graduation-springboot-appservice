//package com.oasis.springboot.repository;
//
//import com.oasis.springboot.domain.calendar.Calendar;
//import com.oasis.springboot.domain.calendar.CalendarRepository;
//import com.oasis.springboot.domain.calendar.CareType;
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
//                .password("qqq")
//                .picture("aaa")
//                .role(Role.USER)
//                .build();
//        userRepository.save(user);
//
//        Calendar calendar = Calendar.builder()
//                .type(CareType.WATER)
//                .plantName("ppl")
//                .user(user)
//                .build();
//        calendarRepository.save(calendar);
//    }
//
//    @Test
//    public void testCalendarFindByUserId() {
//        Long userId = userRepository.findAll().get(0).getId();
//
//        List<Calendar> calendarList = calendarRepository.findAllByUserIdFetchJoin(userId);
//
//        Calendar calendar = calendarList.get(0);
//        assertThat(calendar.getDate()).isEqualTo(LocalDate.now());
//        assertThat(calendar.getType()).isEqualTo(CareType.WATER);
//
//
//    }
//}

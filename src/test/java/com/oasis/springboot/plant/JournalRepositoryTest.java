//package com.oasis.springboot.plant;
//
//import com.oasis.springboot.domain.journal.Journal;
//import com.oasis.springboot.domain.journal.JournalRepository;
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
//public class JournalRepositoryTest {
//    @Autowired
//    JournalRepository journalRepository;
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
//        Journal journal = Journal.builder()
//                .content("content")
//                .picture("picture")
//                .plant(plant)
//                .build();
//        journalRepository.save(journal);
//
//    }
//
//    @AfterEach
//    public void cleanUp() {
//        journalRepository.deleteAllInBatch();
//        plantRepository.deleteAllInBatch();
//        userRepository.deleteAll();
//    }
//
//    @Test
//    public void testFindJournalsByPlantId() {
//        //given
//        String content = "content";
//        Long plantId = plantRepository.findAll().get(0).getId();
//
//        //when
//        List<Journal> journalList = journalRepository.findJournalsByPlantIdFetchJoin(plantId);
//
//        //then
//        Journal journal = journalList.get(0);
//        assertThat(journal.getContent()).isEqualTo(content);
//
//    }
//
//}

package com.oasis.springboot.journal;

import com.oasis.springboot.domain.journal.Journal;
import com.oasis.springboot.domain.journal.JournalRepository;
import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.journal.JournalSaveRequestDto;
import com.oasis.springboot.dto.journal.JournalsResponseDto;
import com.oasis.springboot.dto.plant.PlantSaveRequestDto;
import com.oasis.springboot.service.JournalService;
import com.oasis.springboot.service.PlantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.oasis.springboot.journal.JournalTemplate.*;
import static com.oasis.springboot.plant.PlantTemplate.makePlantSaveRequestDtoWithFile;
import static com.oasis.springboot.user.UserTemplate.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@WithMockUser(username = EMAIL, password = PASSWORD)
public class JournalServiceTest {

    @Autowired
    PlantService plantService;

    @Autowired
    JournalService journalService;

    @Autowired
    JournalRepository journalRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 일지_등록하기_사진과() throws Exception{
        User user = saveUser();
        PlantSaveRequestDto saveRequestDto = makePlantSaveRequestDtoWithFile();
        Long plantId = plantService.savePlant(saveRequestDto);
        JournalSaveRequestDto journalSaveRequestDto = makeJournalSaveRequestDtoWithFile();

        journalService.saveJournal(plantId, journalSaveRequestDto);

        Journal journal = journalRepository.findAll().get(0);

        assertThat(journal.getContent()).isEqualTo(CONTENT1);
        assertThat(journal.getPlant().getId()).isEqualTo(plantId);
    }

    @Test
    public void 일지_등록하기_사진없이() throws Exception {
        User user = saveUser();
        PlantSaveRequestDto saveRequestDto = makePlantSaveRequestDtoWithFile();
        Long plantId = plantService.savePlant(saveRequestDto);
        JournalSaveRequestDto journalSaveRequestDto = makeJournalSaveRequestDtoWithoutFile();

        journalService.saveJournal(plantId, journalSaveRequestDto);

        Journal journal = journalRepository.findAll().get(0);

        assertThat(journal.getContent()).isEqualTo(CONTENT2);
        assertThat(journal.getPicture()).isNull();
    }

    @Test
    public void 일지_가져오기() throws Exception {
        User user = saveUser();
        PlantSaveRequestDto saveRequestDto = makePlantSaveRequestDtoWithFile();

        Long plantId = plantService.savePlant(saveRequestDto);

        JournalSaveRequestDto journalSaveRequestDto1 = makeJournalSaveRequestDtoWithFile();
        JournalSaveRequestDto journalSaveRequestDto2 = makeJournalSaveRequestDtoWithoutFile();

        journalService.saveJournal(plantId, journalSaveRequestDto1);
        journalService.saveJournal(plantId, journalSaveRequestDto2);

        List<JournalsResponseDto> journalsList = journalService.getJournalsLatest(plantId);

        assertThat(journalsList.get(0).getContent()).isEqualTo(CONTENT2);
        assertThat(journalsList.get(1).getContent()).isEqualTo(CONTENT1);
    }

    @Test
    public void 일지_삭제하기() throws Exception {
        User user = saveUser();
        PlantSaveRequestDto saveRequestDto = makePlantSaveRequestDtoWithFile();

        Long plantId = plantService.savePlant(saveRequestDto);

        JournalSaveRequestDto journalSaveRequestDto = makeJournalSaveRequestDtoWithFile();

        journalService.saveJournal(plantId, journalSaveRequestDto);

        List<JournalsResponseDto> journalsList = journalService.getJournalsLatest(plantId);

        Long journalId = journalsList.get(0).getId();
        journalService.deleteJournal(journalId);

        Optional<Journal> journalOptional = journalRepository.findById(journalId);
        assertThat(journalOptional).isEmpty();
    }

    private User saveUser() throws Exception {
        User user = makeTestUser();
        userRepository.save(user);
        return user;
    }
}

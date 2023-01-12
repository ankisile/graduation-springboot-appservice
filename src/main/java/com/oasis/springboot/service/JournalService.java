package com.oasis.springboot.service;

import com.oasis.springboot.domain.journal.Journal;
import com.oasis.springboot.domain.journal.JournalRepository;
import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.plant.PlantRepository;
import com.oasis.springboot.dto.JournalSaveRequestDto;
import com.oasis.springboot.dto.JournalsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;
    private final PlantRepository plantRepository;

    @Transactional
    public void saveJournal(Long plantId, JournalSaveRequestDto journalSaveRequestDto){
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(()->new IllegalArgumentException("해당 식물이 없습니다. id = "+ plantId));

        Long journalId = journalRepository.save(journalSaveRequestDto.toEntity(plant)).getId();
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(()->new IllegalArgumentException("해당 일지가 없습니다. id = "+ journalId));
        plant.updateRecentRecordDate(journal.getCreatedDate().toLocalDate());
    }

    public List<JournalsResponseDto> getJournals(Long plantId){
        return journalRepository.findByPlant_IdOrderByIdDesc(plantId)
                .stream()
                .map(JournalsResponseDto::new)
                .collect(Collectors.toList());

    }

}

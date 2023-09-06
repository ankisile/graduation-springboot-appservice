package com.oasis.springboot.service;

import com.oasis.springboot.common.exception.InvalidatePlantException;
import com.oasis.springboot.common.handler.S3Uploader;
import com.oasis.springboot.domain.journal.Journal;
import com.oasis.springboot.domain.journal.JournalRepository;
import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.plant.PlantRepository;
import com.oasis.springboot.dto.journal.JournalSaveRequestDto;
import com.oasis.springboot.dto.journal.JournalsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JournalService {

    private final JournalRepository journalRepository;
    private final PlantRepository plantRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public String saveJournal(Long plantId, JournalSaveRequestDto requestDto) {

        String s3Url = null;
        if (requestDto.getIsFileExist().equals("true")) {
            s3Url = s3Uploader.getFileS3Url(requestDto.getFile(), "journal");
        }

        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(InvalidatePlantException::new);

        Long journalId = journalRepository.save(requestDto.toEntity(plant, s3Url)).getId();
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일지가 없습니다. id = " + journalId));
        plant.updateRecentRecordDate(journal.getCreatedDate().toLocalDate());
        return "식물 일지 등록 성공";
    }

    public List<JournalsResponseDto> getJournals(Long plantId) {
        return journalRepository.findJournalsByPlantIdFetchJoin(plantId)
                .stream()
                .map(JournalsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public String deleteJournal(Long journalId) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일지가 없습니다. id = " + journalId));
        if (journal.getPicture() != null) {
            String str = journal.getPicture();
            String path = str.substring(62);
            s3Uploader.delete(path);
        }
        journalRepository.delete(journal);
        return "식물 일지 삭제 성공";
    }
}

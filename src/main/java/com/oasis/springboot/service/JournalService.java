package com.oasis.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.springboot.domain.journal.Journal;
import com.oasis.springboot.domain.journal.JournalRepository;
import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.plant.PlantRepository;
import com.oasis.springboot.dto.journal.JournalSaveRequestDto;
import com.oasis.springboot.dto.journal.JournalsResponseDto;
import com.oasis.springboot.common.handler.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;
    private final PlantRepository plantRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public String saveJournal(Long plantId, String dto, MultipartFile file){
        ObjectMapper mapper = new ObjectMapper();
        JournalSaveRequestDto requestDto = null;
        try {
            requestDto = mapper.readValue(dto, JournalSaveRequestDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if(file!=null) {
                String s3Url = s3Uploader.upload(file, "static");
                requestDto.setPicture(s3Url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(()->new IllegalArgumentException("해당 식물이 없습니다. id = "+ plantId));

        Long journalId = journalRepository.save(requestDto.toEntity(plant)).getId();
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(()->new IllegalArgumentException("해당 일지가 없습니다. id = "+ journalId));
        plant.updateRecentRecordDate(journal.getCreatedDate().toLocalDate());
        return "식물 일지 등록 성공";
    }

    public List<JournalsResponseDto> getJournals(Long plantId){
        return journalRepository.findJournalsByPlantIdFetchJoin(plantId)
                .stream()
                .map(JournalsResponseDto::new)
                .collect(Collectors.toList());
    }

}

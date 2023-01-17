package com.oasis.springboot.controller;

import com.oasis.springboot.dto.journal.JournalSaveRequestDto;
import com.oasis.springboot.dto.journal.JournalsResponseDto;
import com.oasis.springboot.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/journals")
public class JournalController {
    private final JournalService journalService;

    @PostMapping("/{plantId}")
    public void saveJournal(@PathVariable Long plantId, @RequestBody JournalSaveRequestDto requestDto){
        journalService.saveJournal(plantId, requestDto);
    }

    @GetMapping("/{plantId}")
    public List<JournalsResponseDto> getJournals(@PathVariable Long plantId) {
        return journalService.getJournals(plantId);
    }
}

package com.oasis.springboot.controller;

import com.oasis.springboot.dto.journal.JournalSaveRequestDto;
import com.oasis.springboot.dto.journal.JournalsResponseDto;
import com.oasis.springboot.response.ListResponse;
import com.oasis.springboot.response.ResponseService;
import com.oasis.springboot.response.SingleResponse;
import com.oasis.springboot.service.JournalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Journal", description = "식물일지 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/journals")
public class JournalController {
    private final JournalService journalService;
    private final ResponseService responseService;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/{plantId}")
    public SingleResponse<String> saveJournal(@PathVariable Long plantId, @RequestBody JournalSaveRequestDto requestDto){
        return responseService.getSingleResponse(journalService.saveJournal(plantId, requestDto));
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/{plantId}")
    public ListResponse<JournalsResponseDto> getJournals(@PathVariable Long plantId) {
        return responseService.getListResponse(journalService.getJournals(plantId));
    }
}

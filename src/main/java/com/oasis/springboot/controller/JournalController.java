package com.oasis.springboot.controller;

import com.oasis.springboot.dto.journal.JournalsResponseDto;
import com.oasis.springboot.common.response.ListResponse;
import com.oasis.springboot.common.response.ResponseService;
import com.oasis.springboot.common.response.SingleResponse;
import com.oasis.springboot.service.JournalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "Journal", description = "식물일지 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/journals")
public class JournalController {
    private final JournalService journalService;
    private final ResponseService responseService;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping(value = "/{plantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SingleResponse<String> saveJournal(@PathVariable Long plantId,
                                              @Schema(name = "JournalSaveRequestDto",
                                                      description = "식물일지 저장(String이지만 json형식으로)",
                                                      required = true,
                                                      example = "{\n" +
                                                              "  \"content\": \"일기내용\"\n" +
                                                              "}"
                                              )
                                              @RequestPart("key") String requestDto,
                                              @Parameter(name = "file", description = "사진(maxSize: 10MB)")
                                              @RequestPart(value = "file", required = false) MultipartFile multipartFile){
        return responseService.getSingleResponse(journalService.saveJournal(plantId, requestDto, multipartFile));
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/{plantId}")
    public ListResponse<JournalsResponseDto> getJournals(@PathVariable Long plantId) {
        return responseService.getListResponse(journalService.getJournals(plantId));
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @DeleteMapping("/{journalId}")
    public SingleResponse<String> deleteJournal(@PathVariable Long journalId) {
        return responseService.getSingleResponse(journalService.deleteJournal(journalId));
    }
}

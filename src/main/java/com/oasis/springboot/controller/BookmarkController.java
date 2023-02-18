package com.oasis.springboot.controller;

import com.oasis.springboot.common.response.ListResponse;
import com.oasis.springboot.common.response.ResponseService;
import com.oasis.springboot.common.response.SingleResponse;
import com.oasis.springboot.dto.garden.GardenListResponseDto;
import com.oasis.springboot.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "북마크")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final ResponseService responseService;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/{gardenId}")
    public SingleResponse<String> pushBookmark(@PathVariable Long gardenId){
        return responseService.getSingleResponse(bookmarkService.pushBookmark(gardenId));
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, description = "북마크 리스트")
    @GetMapping()
    public ListResponse<GardenListResponseDto> getBookmarkGardenList(){
        return responseService.getListResponse(bookmarkService.getBookmarkList());
    }

}

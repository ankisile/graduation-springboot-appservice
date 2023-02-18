package com.oasis.springboot.service;

import com.oasis.springboot.common.exception.InvalidateGardenException;
import com.oasis.springboot.domain.bookmark.Bookmark;
import com.oasis.springboot.domain.bookmark.BookmarkRepository;
import com.oasis.springboot.domain.garden.Garden;
import com.oasis.springboot.domain.garden.GardenRepository;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.garden.GardenListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final GardenRepository gardenRepository;
    private final UserService userService;

    @Transactional
    public String pushBookmark(Long gardenId){

        Long userId = userService.findUserId();
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findBookmarkByUserIdAndGardenId(userId, gardenId);

        if(optionalBookmark.isEmpty()){
            User user = userService.findByEmail();
            Garden garden = gardenRepository.findById(gardenId).orElseThrow(InvalidateGardenException::new);

            Bookmark bookmark = Bookmark.builder()
                            .user(user)
                            .garden(garden)
                            .build();

            bookmarkRepository.save(bookmark);

            return "Bookmark Add";
        }
        else{
            Bookmark bookmark = optionalBookmark.get();
            bookmarkRepository.delete(bookmark);
            return "Bookmark Delete";
        }
    }

    public List<GardenListResponseDto> getBookmarkList(){
        Long userId = userService.findUserId();
        List<Bookmark> bookmarkList = bookmarkRepository.findBookmarkGardenListByUserId(userId);
        List<Garden> gardenList = new ArrayList<>();
        for(Bookmark bookmark : bookmarkList){
            gardenList.add(bookmark.getGarden());
        }
        return gardenList.stream()
                .map(GardenListResponseDto::new)
                .collect(Collectors.toList());
    }

    public boolean isBookmark(Long gardenId){
        Long userId = userService.findUserId();
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findBookmarkByUserIdAndGardenId(userId, gardenId);
        if(optionalBookmark.isPresent())
            return true;
        else
            return false;
    }

}

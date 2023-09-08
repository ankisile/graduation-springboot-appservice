package com.oasis.springboot.bookmark;

import com.oasis.springboot.domain.bookmark.Bookmark;
import com.oasis.springboot.domain.bookmark.BookmarkRepository;
import com.oasis.springboot.domain.garden.GardenRepository;
import com.oasis.springboot.domain.user.User;
import com.oasis.springboot.domain.user.UserRepository;
import com.oasis.springboot.dto.garden.GardenListResponseDto;
import com.oasis.springboot.service.BookmarkService;
import com.oasis.springboot.service.GardenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.oasis.springboot.user.UserTemplate.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@WithMockUser(username = EMAIL, password = PASSWORD)
public class BookmarkServiceTest {

    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    GardenService gardenService;

    @Autowired
    GardenRepository gardenRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 북마크_등록() throws Exception {
        User user = saveUser();

        List<GardenListResponseDto> gardenList = gardenService.getGardenList();
        Long gardenId = gardenList.get(0).getId();

        bookmarkService.pushBookmark(gardenId);

        Bookmark bookmark = bookmarkRepository.findAll().get(0);

        assertThat(bookmark.getGarden().getId()).isEqualTo(gardenId);
    }

    @Test
    public void 북마크_가져오기() throws Exception {
        User user = saveUser();

        List<GardenListResponseDto> gardenList = gardenService.getGardenList();
        Long gardenId = gardenList.get(0).getId();

        bookmarkService.pushBookmark(gardenId);

        List<GardenListResponseDto> bookmarkList = bookmarkService.getBookmarkList();

        String bookmarkGardenName = bookmarkList.get(0).getName();
        String gardenName = gardenRepository.findById(gardenId).get().getName();

        assertThat(bookmarkGardenName).isEqualTo(gardenName);
    }

    @Test
    public void 북마크_확인() throws Exception {
        User user = saveUser();

        List<GardenListResponseDto> gardenList = gardenService.getGardenList();
        Long gardenId = gardenList.get(0).getId();

        bookmarkService.pushBookmark(gardenId);

        Boolean value = bookmarkService.isBookmark(gardenId);

        assertThat(value).isTrue();
    }


    private User saveUser() throws Exception {
        User user = makeTestUser();
        userRepository.save(user);
        return user;
    }
}

package com.oasis.springboot.domain.bookmark;

import com.oasis.springboot.domain.garden.Garden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Query("select b from Bookmark b join fetch b.user u join fetch b.garden g where u.id = ?1 and g.id = ?2")
    Optional<Bookmark> findBookmarkByUserIdAndGardenId(Long userId, Long GardenId);

    @Query("select b from Bookmark b join fetch b.user u join fetch b.garden g where u.id = ?1")
    List<Bookmark> findBookmarkGardenListByUserId(Long userId);
}

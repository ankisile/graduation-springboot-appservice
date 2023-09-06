package com.oasis.springboot.domain.journal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {
    //좀 더 살펴봐야됨
    @Query("select j from Journal j join fetch j.plant p join fetch p.user where p.id = ?1 order by j.id desc")
    List<Journal> findJournalsByPlantIdFetchJoinLatest(Long plantId);
}

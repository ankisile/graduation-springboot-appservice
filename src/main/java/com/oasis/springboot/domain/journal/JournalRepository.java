package com.oasis.springboot.domain.journal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {

    List<Journal> findByPlant_IdOrderByIdDesc(Long plantId);
}

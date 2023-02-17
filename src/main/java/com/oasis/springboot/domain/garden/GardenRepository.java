package com.oasis.springboot.domain.garden;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GardenRepository extends JpaRepository<Garden, Long> {
    List<Garden> findByNameContaining(String keyword);
}

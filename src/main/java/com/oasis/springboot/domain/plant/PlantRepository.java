package com.oasis.springboot.domain.plant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    List<Plant> findByUser_Id(Long userId);
}

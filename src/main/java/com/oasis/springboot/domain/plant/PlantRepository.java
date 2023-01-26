package com.oasis.springboot.domain.plant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    //일단 FETCH JOIN으로 N+1 문제 해결
    @Query("select t from Plant t join fetch t.user where t.user.id = ?1")
    List<Plant> findByUser_Id(Long userId);
}

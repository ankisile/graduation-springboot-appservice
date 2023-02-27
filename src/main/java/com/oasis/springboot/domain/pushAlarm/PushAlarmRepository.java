package com.oasis.springboot.domain.pushAlarm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PushAlarmRepository extends JpaRepository<PushAlarm, Long> {
    @Query("select pa from PushAlarm pa join fetch pa.user join fetch pa.plant where pa.plant.id = ?1")
    List<PushAlarm> findAllByPlantId(Long plantId);

    @Query("select pa from PushAlarm pa join fetch pa.user join fetch pa.plant where pa.date = ?1")
    List<PushAlarm> findAllByDate(LocalDate date);
}

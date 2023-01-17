package com.oasis.springboot.domain.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
//    @Query(value = "select * from Calendar c inner join Plant p on c.plant_id = p.id where c.user_id = ?1 ", nativeQuery = true)
//    List<Calendar> findByUserId(Long userId);

    List<Calendar> findByUser_Id(Long userId);
}

package com.oasis.springboot.domain.calendar;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private String type;
}

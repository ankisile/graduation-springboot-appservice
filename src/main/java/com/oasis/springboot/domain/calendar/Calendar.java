package com.oasis.springboot.domain.calendar;

import com.oasis.springboot.domain.BaseTimeEntity;
import com.oasis.springboot.domain.plant.Plant;
import com.oasis.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
public class Calendar extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    //Enum으로 가야될거 같음
    @Enumerated(EnumType.STRING)
    @Column
    private CareType type;

    @Column
    private String plantName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @Column
//    private Long plantId;
//
//    @Column
//    private Long userId;

    @Builder
    public Calendar(CareType type, String plantName, User user) {
        this.date = LocalDate.now();
        this.type = type;
        this.plantName = plantName;
        this.user = user;
//        this.plantId = plant.getId();
//        this.userId = user.getId();
    }
}

package com.oasis.springboot.domain.plant;

import com.oasis.springboot.domain.BaseTimeEntity;
import com.oasis.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Plant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String picture;

    @Column
    private LocalDate adoptingDate;

    @Column
    private Integer waterAlarmInterval;

    @Column
    private String waterSupply;

    @Column
    private Double sunshine;

    @Column
    private Integer highTemperature;

    @Column
    private Integer lowTemperature;

    @Column
    private LocalDate recentRecordDate;

    private Boolean star;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Plant(String name, String picture, LocalDate adoptingDate, Integer waterAlarmInterval, String waterSupply, Double sunshine, Integer highTemperature, Integer lowTemperature, User user) {
        this.name = name;
        this.picture = picture;
        this.adoptingDate = adoptingDate;
        this.waterAlarmInterval = waterAlarmInterval;
        this.waterSupply = waterSupply;
        this.sunshine = sunshine;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
        this.recentRecordDate = LocalDate.now();
        this.user = user;
        this.star = false;
    }

    public void updateRecentRecordDate(LocalDate date){
        this.recentRecordDate = date;
    }

    public void updatePlantStar(Boolean star){
        this.star = star;
    }

}

package com.oasis.springboot.domain.plant;

import com.oasis.springboot.domain.BaseTimeEntity;
import com.oasis.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Integer waterInterval;

    @Column
    private Integer nutritionInterval;

    @Column
    private Integer repottingInterval;

    @Column
    private Double sunshine;

    @Column
    private Double waterSupply;

    @Column
    private Integer highTemperature;

    @Column
    private Integer lowTemperature;

    @Column
    private LocalDate recentRecordDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Plant(String name, String picture, LocalDate adoptingDate, Integer waterInterval, Integer nutritionInterval, Integer repottingInterval, Double sunshine, Double waterSupply, Integer highTemperature, Integer lowTemperature, User user) {
        this.name = name;
        this.picture = picture;
        this.adoptingDate = adoptingDate;
        this.waterInterval = waterInterval;
        this.nutritionInterval = nutritionInterval;
        this.repottingInterval = repottingInterval;
        this.sunshine = sunshine;
        this.waterSupply = waterSupply;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
        this.recentRecordDate = LocalDate.now();
        this.user = user;
    }

    public void updateRecentRecordDate(LocalDate date){
        this.recentRecordDate = date;
    }

}

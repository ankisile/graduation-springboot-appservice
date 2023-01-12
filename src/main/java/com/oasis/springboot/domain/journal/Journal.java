package com.oasis.springboot.domain.journal;


import com.oasis.springboot.domain.BaseTimeEntity;
import com.oasis.springboot.domain.plant.Plant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Journal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private String picture;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Builder
    public Journal(String content, String picture, Plant plant) {
        this.content = content;
        this.picture = picture;
        this.plant = plant;
    }
}

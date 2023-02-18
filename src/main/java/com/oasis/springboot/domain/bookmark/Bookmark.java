package com.oasis.springboot.domain.bookmark;

import com.oasis.springboot.domain.BaseTimeEntity;
import com.oasis.springboot.domain.garden.Garden;
import com.oasis.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "garden_id")
    private Garden garden;

    @Builder
    public Bookmark(User user, Garden garden) {
        this.user = user;
        this.garden = garden;
    }
}

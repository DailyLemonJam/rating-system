package com.leverx.ratingsystem.model.rating;

import com.leverx.ratingsystem.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User seller;

    private double totalRating;

}

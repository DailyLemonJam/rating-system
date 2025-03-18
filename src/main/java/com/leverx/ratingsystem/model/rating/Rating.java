package com.leverx.ratingsystem.model.rating;

import com.leverx.ratingsystem.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name = "ratings")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rating_id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double averageRating;

    private Integer totalRatings;

}

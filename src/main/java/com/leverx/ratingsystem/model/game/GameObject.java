package com.leverx.ratingsystem.model.game;

import com.leverx.ratingsystem.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "gameobjects")
public class GameObject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "gameobject_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Instant createdAt;

    private Instant updatedAt;

}

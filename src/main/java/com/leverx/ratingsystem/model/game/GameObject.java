package com.leverx.ratingsystem.model.game;

import com.leverx.ratingsystem.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class GameObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Game game;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

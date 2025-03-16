package com.leverx.ratingsystem.model.game;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer id;

    private String name;

}

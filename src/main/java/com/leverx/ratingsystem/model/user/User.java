package com.leverx.ratingsystem.model.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private UUID id;

    private String nickname;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private LocalDateTime createdAt;

    private Role role;

}

package com.leverx.ratingsystem.model.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String nickname;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private LocalDateTime createdAt;

    private Role role;

}

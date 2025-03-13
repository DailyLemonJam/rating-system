package com.leverx.ratingsystem.model.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nickname;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private LocalDateTime createdAt;

    private Role role;

}

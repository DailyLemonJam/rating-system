package com.leverx.ratingsystem.config;

import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfiguration {
    public static final Integer MIN_GRADE = 1;
    public static final Integer MAX_GRADE = 5;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    public static final List<String> AVAILABLE_GAMES = List.of(
            "CS2",
            "Dota",
            "FIFA",
            "Team Fortress"
    );
}

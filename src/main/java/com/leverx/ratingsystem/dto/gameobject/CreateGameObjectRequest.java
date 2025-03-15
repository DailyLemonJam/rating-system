package com.leverx.ratingsystem.dto.gameobject;

import java.util.UUID;

public record CreateGameObjectRequest(String title,
                                      String description,
                                      UUID gameId) {
}

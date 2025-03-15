package com.leverx.ratingsystem.dto.gameobject;

import java.time.Instant;
import java.util.UUID;

public record GameObjectDto(String title,
                            String description,
                            Instant createdAt,
                            Instant updatedAt,
                            UUID userId) {
}

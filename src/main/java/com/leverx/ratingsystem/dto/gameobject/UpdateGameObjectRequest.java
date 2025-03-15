package com.leverx.ratingsystem.dto.gameobject;

import java.util.UUID;

public record UpdateGameObjectRequest(String newTitle,
                                      String newDescription) {
}

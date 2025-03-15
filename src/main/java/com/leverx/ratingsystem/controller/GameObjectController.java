package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.dto.gameobject.CreateGameObjectRequest;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.dto.gameobject.UpdateGameObjectRequest;
import com.leverx.ratingsystem.service.GameObjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/objects")
public class GameObjectController {

    private final GameObjectService gameObjectService;

    public GameObjectController(GameObjectService gameObjectService) {
        this.gameObjectService = gameObjectService;
    }

    @GetMapping("/{id}")
    public GameObjectDto getGameObjectById(@PathVariable UUID gameObjectId) {

    }

    @PostMapping
    public ResponseEntity<GameObjectDto> createGameObject(@Valid @RequestBody CreateGameObjectRequest createGameObjectRequest) {

    }

    @PutMapping("/{id}")
    public ResponseEntity<GameObjectDto> updateGameObjectById(@PathVariable UUID gameObjectId,
                                                              @Valid @RequestBody UpdateGameObjectRequest updateGameObjectRequest) {

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameObjectById(@PathVariable UUID gameObjectId) {

    }
}

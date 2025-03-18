package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.dto.gameobject.CreateGameObjectRequest;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.dto.gameobject.UpdateGameObjectRequest;
import com.leverx.ratingsystem.service.GameObjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/objects")
public class GameObjectController {
    private final GameObjectService gameObjectService;

    @GetMapping("/game/{id}")
    public ResponseEntity<List<GameObjectDto>> getAllGameObjectsByGame(@PathVariable Integer id) {
        var gameObjectDtos = gameObjectService.getAllGameObjectsByGame(id);
        return new ResponseEntity<>(gameObjectDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameObjectDto> getGameObjectById(@PathVariable UUID id) {
        var gameObjectDto = gameObjectService.getGameObjectById(id);
        return new ResponseEntity<>(gameObjectDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GameObjectDto> createGameObject(@Valid @RequestBody CreateGameObjectRequest createGameObjectRequest,
                                                          Principal principal) {
        var commentDto = gameObjectService.createGameObject(createGameObjectRequest, principal);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameObjectDto> updateGameObjectById(@PathVariable UUID id,
                                                              @Valid @RequestBody UpdateGameObjectRequest updateGameObjectRequest,
                                                              Principal principal) {
        var gameObjectDto = gameObjectService.updateGameObjectById(id, updateGameObjectRequest, principal);
        return new ResponseEntity<>(gameObjectDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameObjectById(@PathVariable UUID id,
                                                     Principal principal) {
        gameObjectService.deleteGameObjectById(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.gameobject.CreateGameObjectRequest;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.dto.gameobject.UpdateGameObjectRequest;
import com.leverx.ratingsystem.exception.GameNotFoundException;
import com.leverx.ratingsystem.exception.GameObjectNotFoundException;
import com.leverx.ratingsystem.mapper.GameObjectMapper;
import com.leverx.ratingsystem.model.game.GameObject;
import com.leverx.ratingsystem.repository.GameObjectRepository;
import com.leverx.ratingsystem.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class GameObjectService {

    private final GameRepository gameRepository;
    private final GameObjectRepository gameObjectRepository;
    private final GameObjectMapper gameObjectMapper;

    public GameObjectService(GameRepository gameRepository, GameObjectRepository gameObjectRepository, GameObjectMapper gameObjectMapper) {
        this.gameRepository = gameRepository;
        this.gameObjectRepository = gameObjectRepository;
        this.gameObjectMapper = gameObjectMapper;
    }

    public GameObjectDto getGameObjectById(UUID gameObjectId) {
        var gameObject = gameObjectRepository
                .findById(gameObjectId)
                .orElseThrow(() -> new GameObjectNotFoundException("Can't find object with id " + gameObjectId));
        return gameObjectMapper.toDto(gameObject);
    }

    public GameObjectDto createGameObject(CreateGameObjectRequest createGameObjectRequest) {
        var game = gameRepository.findById(createGameObjectRequest.gameId())
                .orElseThrow(() -> new GameNotFoundException("Can't find game"));
        var instantNow = Instant.now();
        var gameObject = GameObject.builder()
                .title(createGameObjectRequest.title())
                .description(createGameObjectRequest.description())
                .game(game)
                .createdAt(instantNow)
                .updatedAt(instantNow)
                // TODO: User
                .build();
        var newGameObject = gameObjectRepository.save(gameObject);
        return gameObjectMapper.toDto(newGameObject);
    }

    public GameObjectDto updateGameObjectById(UUID gameObjectId, UpdateGameObjectRequest updateGameObjectRequest) {
        var gameObject = gameObjectRepository.findById(gameObjectId)
                .orElseThrow(() -> new GameObjectNotFoundException("Can't find object with id " + gameObjectId));
        gameObject.setTitle(updateGameObjectRequest.newTitle());
        gameObject.setDescription(updateGameObjectRequest.newDescription());
        gameObjectRepository.save(gameObject);
        return gameObjectMapper.toDto(gameObject);
    }

    public void deleteGameObjectById(UUID gameObjectId) {
        if (gameObjectRepository.existsById(gameObjectId)) {
            gameObjectRepository.deleteById(gameObjectId);
            return;
        }
        throw new GameObjectNotFoundException("Can't find gameobject with id " + gameObjectId);
    }

}

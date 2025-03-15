package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.gameobject.CreateGameObjectRequest;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.exception.ObjectNotFoundException;
import com.leverx.ratingsystem.mapper.GameObjectMapper;
import com.leverx.ratingsystem.repository.GameObjectRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameObjectService {

    private final GameObjectRepository gameObjectRepository;
    private final GameObjectMapper gameObjectMapper;

    public GameObjectService(GameObjectRepository gameObjectRepository, GameObjectMapper gameObjectMapper) {
        this.gameObjectRepository = gameObjectRepository;
        this.gameObjectMapper = gameObjectMapper;
    }

    public GameObjectDto getGameObjectById(UUID gameObjectId) {
        var gameObject = gameObjectRepository
                .findById(gameObjectId)
                .orElseThrow(() -> new ObjectNotFoundException("Can't find object with id " + gameObjectId));
        return gameObjectMapper.toDto(gameObject);
    }

    public GameObjectDto createGameObject(CreateGameObjectRequest createGameObjectRequest) {

    }

}

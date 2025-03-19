package com.leverx.ratingsystem.service.gameobject;

import com.leverx.ratingsystem.dto.gameobject.CreateGameObjectRequest;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.dto.gameobject.UpdateGameObjectRequest;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface GameObjectService {
    List<GameObjectDto> getAllGameObjectsByGame(Integer gameId);

    GameObjectDto getGameObjectById(UUID gameObjectId);

    GameObjectDto createGameObject(CreateGameObjectRequest createGameObjectRequest, Principal principal);

    GameObjectDto updateGameObjectById(UUID gameObjectId, UpdateGameObjectRequest updateGameObjectRequest, Principal principal);

    void deleteGameObjectById(UUID gameObjectId, Principal principal);
}

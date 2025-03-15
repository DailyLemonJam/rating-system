package com.leverx.ratingsystem.mapper;

import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.model.game.GameObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameObjectMapper {

    public GameObjectDto toDto(GameObject gameObject) {
        return new GameObjectDto(gameObject.getTitle(), gameObject.getDescription(),
                gameObject.getCreatedAt(), gameObject.getUpdatedAt(), gameObject.getUser().getId());
    }

    public GameObject toGameObject(GameObjectDto gameObjectDto) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public List<GameObjectDto> toDto(List<GameObject> gameObjects) {
        var gameObjectDtos = new ArrayList<GameObjectDto>();
        for (var gameObject : gameObjects) {
            gameObjectDtos.add(toDto(gameObject));
        }
        return gameObjectDtos;
    }

    public List<GameObject> ToGameObject(List<GameObjectDto> gameObjectsDtos) {
        var gameObjects = new ArrayList<GameObject>();
        for (var gameObjectDto : gameObjectsDtos) {
            gameObjects.add(toGameObject(gameObjectDto));
        }
        return gameObjects;
    }

}

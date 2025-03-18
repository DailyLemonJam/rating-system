package com.leverx.ratingsystem.mapper.gameobject;

import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.game.GameObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameObjectMapper implements ModelDtoMapper<GameObjectDto, GameObject> {

    @Override
    public GameObjectDto toDto(GameObject gameObject) {
        return new GameObjectDto(gameObject.getTitle(),
                gameObject.getId(),
                gameObject.getDescription(),
                gameObject.getCreatedAt(),
                gameObject.getUpdatedAt(),
                gameObject.getUser().getId());
    }

    @Override
    public GameObject toModel(GameObjectDto gameObjectDto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<GameObjectDto> toDto(List<GameObject> gameObjects) {
        var gameObjectDtos = new ArrayList<GameObjectDto>();
        for (var gameObject : gameObjects) {
            gameObjectDtos.add(toDto(gameObject));
        }
        return gameObjectDtos;
    }

    @Override
    public List<GameObject> toModel(List<GameObjectDto> gameObjectDtos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

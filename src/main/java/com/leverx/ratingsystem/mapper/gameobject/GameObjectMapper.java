package com.leverx.ratingsystem.mapper.gameobject;

import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.game.GameObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameObjectMapper implements ModelDtoMapper<GameObjectDto, GameObject> {

    @Override
    public GameObjectDto toDto(GameObject gameObject) {
        return null;
    }

    @Override
    public GameObject toModel(GameObjectDto gameObjectDto) {
        return null;
    }

    @Override
    public List<GameObjectDto> toDto(List<GameObject> gameObjects) {
        return List.of();
    }

    @Override
    public List<GameObject> toModel(List<GameObjectDto> gameObjectDtos) {
        return List.of();
    }
}

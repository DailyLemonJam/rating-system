package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.model.game.Game;
import com.leverx.ratingsystem.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public Optional<Game> findById(Integer id) {
        return gameRepository.findById(id);
    }

    public Optional<Game> findByName(String name) {
        return gameRepository.findByName(name);
    }

}

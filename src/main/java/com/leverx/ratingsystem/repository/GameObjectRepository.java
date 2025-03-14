package com.leverx.ratingsystem.repository;

import com.leverx.ratingsystem.model.game.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, UUID> {
}

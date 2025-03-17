package com.leverx.ratingsystem.repository;

import com.leverx.ratingsystem.model.user.User;
import com.leverx.ratingsystem.model.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    List<User> findAllByStatus(UserStatus status);
    Boolean existsByEmail(String email);
    Boolean existsByName(String name);
}

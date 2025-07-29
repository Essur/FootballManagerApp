package com.example.footballmanagerapp.common.repositroy;

import com.example.footballmanagerapp.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    Player save(Player player);
    Optional<Player> findById(Long id);
    List<Player> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}

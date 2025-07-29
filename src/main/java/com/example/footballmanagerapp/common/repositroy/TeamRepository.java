package com.example.footballmanagerapp.common.repositroy;

import com.example.footballmanagerapp.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    Team save(Team team);
    Optional<Team> findById(Long id);
    List<Team> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}

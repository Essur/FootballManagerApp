package com.example.footballmanagerapp.jdbc.service;

import com.example.footballmanagerapp.common.service.AbstractPlayerService;
import com.example.footballmanagerapp.entity.Player;
import com.example.footballmanagerapp.jdbc.repository.JdbcPlayerRepository;
import com.example.footballmanagerapp.jdbc.repository.JdbcTeamRepository;
import com.example.footballmanagerapp.mapper.PlayerMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JdbcPlayerService extends AbstractPlayerService {
    private final JdbcPlayerRepository playerRepository;

    public JdbcPlayerService(PlayerMapper playerMapper,
                             JdbcTeamRepository teamRepository,
                             JdbcPlayerRepository playerRepository) {
        super(playerMapper, teamRepository);
        this.playerRepository = playerRepository;
    }

    @Override
    protected Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    protected Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    protected List<Player> findAllEntities() {
        return playerRepository.findAll();
    }

    @Override
    protected void deleteById(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    protected boolean existsById(Long id) {
        return playerRepository.existsById(id);
    }
}

package com.example.footballmanagerapp.hibernate.service;

import com.example.footballmanagerapp.common.service.AbstractPlayerService;
import com.example.footballmanagerapp.entity.Player;
import com.example.footballmanagerapp.hibernate.repository.HibernatePlayerRepository;
import com.example.footballmanagerapp.hibernate.repository.HibernateTeamRepository;
import com.example.footballmanagerapp.mapper.PlayerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HibernatePlayerService extends AbstractPlayerService {
    private final HibernatePlayerRepository playerRepository;

    public HibernatePlayerService(PlayerMapper playerMapper,
                                  HibernateTeamRepository teamRepository,
                                  HibernatePlayerRepository playerRepository) {
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

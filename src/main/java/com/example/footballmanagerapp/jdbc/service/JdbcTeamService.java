package com.example.footballmanagerapp.jdbc.service;

import com.example.footballmanagerapp.common.service.AbstractTeamService;
import com.example.footballmanagerapp.entity.Team;
import com.example.footballmanagerapp.jdbc.repository.JdbcTeamRepository;
import com.example.footballmanagerapp.mapper.TeamMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JdbcTeamService extends AbstractTeamService {
    private final JdbcTeamRepository teamRepository;

    protected JdbcTeamService(TeamMapper teamMapper, JdbcTeamRepository teamRepository) {
        super(teamMapper);
        this.teamRepository = teamRepository;
    }

    @Override
    protected Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    protected Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    protected List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    protected void deleteById(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    protected boolean existsById(Long id) {
        return teamRepository.existsById(id);
    }
}

package com.example.footballmanagerapp.common.service;

import com.example.footballmanagerapp.dto.request.TeamSaveDto;
import com.example.footballmanagerapp.dto.resonse.TeamDto;
import com.example.footballmanagerapp.entity.Team;
import com.example.footballmanagerapp.exception.DataNotFoundException;
import com.example.footballmanagerapp.mapper.TeamMapper;

import java.util.List;
import java.util.Optional;

public abstract class AbstractTeamService {
    protected final TeamMapper teamMapper;

    protected AbstractTeamService(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    protected abstract Optional<Team> findById(Long id);
    protected abstract Team save(Team team);
    protected abstract List<Team> findAll();
    protected abstract void deleteById(Long id);
    protected abstract boolean existsById(Long id);

    public Long createTeam(TeamSaveDto dto) {
        Team team = teamMapper.toEntity(dto);
        return save(team).getId();
    }

    public TeamDto updateTeam(TeamSaveDto dto, Long id) {
        Team team = findById(id).orElseThrow(() -> new DataNotFoundException("Team with id " + id + " not found"));
        teamMapper.updateEntity(dto, team);
        return teamMapper.toDto(save(team));
    }

    public List<TeamDto> findAllTeams() {
        return findAll().stream().map(teamMapper::toDto).toList();
    }

    public TeamDto findTeamById(Long id) {
        Team team = findById(id).orElseThrow(() -> new DataNotFoundException("Team with id " + id + " not found"));
        return teamMapper.toDto(team);
    }

    public void deleteTeamById(Long id) {
        if (!existsById(id)) {
            throw new DataNotFoundException("Team with id " + id + " not found");
        }
        deleteById(id);
    }
}

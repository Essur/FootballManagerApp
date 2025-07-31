package com.example.footballmanagerapp.common.service;

import com.example.footballmanagerapp.common.repositroy.TeamRepository;
import com.example.footballmanagerapp.dto.request.PlayerSaveDto;
import com.example.footballmanagerapp.dto.request.TransferRequestDto;
import com.example.footballmanagerapp.dto.resonse.PlayerDto;
import com.example.footballmanagerapp.entity.Player;
import com.example.footballmanagerapp.entity.Team;
import com.example.footballmanagerapp.exception.DataNotFoundException;
import com.example.footballmanagerapp.mapper.PlayerMapper;
import com.example.footballmanagerapp.util.PlayerPriceCalculatorUtil;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public abstract class AbstractPlayerService {
    protected final PlayerMapper playerMapper;
    protected final TeamRepository teamRepository;

    protected AbstractPlayerService(PlayerMapper playerMapper, TeamRepository teamRepository) {
        this.playerMapper = playerMapper;
        this.teamRepository = teamRepository;
    }

    protected abstract Optional<Player> findById(Long id);

    protected abstract Player save(Player player);

    protected abstract List<Player> findAllEntities();

    protected abstract void deleteById(Long id);

    protected abstract boolean existsById(Long id);

    @Transactional
    public Long createPlayer(PlayerSaveDto playerDto) {
        Team team = teamRepository.findById(playerDto.teamId())
                .orElseThrow(() -> new DataNotFoundException("Team with id " + playerDto.teamId() + " not found"));

        Player player = playerMapper.toEntity(playerDto);
        player.setTeam(team);

        return save(player).getId();
    }

    @Transactional
    public PlayerDto updatePlayer(PlayerSaveDto playerDto, Long playerId) {
        Player player = findById(playerId)
                .orElseThrow(() -> new DataNotFoundException("Player with id " + playerId + " not found"));
        playerMapper.updateEntity(playerDto, player);
        return playerMapper.toPlayerDto(save(player));
    }

    public List<PlayerDto> findAll() {
        return findAllEntities().stream().map(playerMapper::toPlayerDto).toList();
    }

    public PlayerDto findByIdDto(Long playerId) {
        Player player = findById(playerId)
                .orElseThrow(() -> new DataNotFoundException("Player with id " + playerId + " not found"));
        return playerMapper.toPlayerDto(player);
    }

    @Transactional
    public void deletePlayer(Long playerId) {
        if (!existsById(playerId)) {
            throw new DataNotFoundException("Player with id " + playerId + " not found");
        }
        deleteById(playerId);
    }

    @Transactional
    public PlayerDto transferPlayer(Long playerId, TransferRequestDto transferRequestDto) {
        Player player = findById(playerId)
                .orElseThrow(() -> new DataNotFoundException("Player with id " + playerId + " not found"));

        if (player.getTeam().getId().equals(transferRequestDto.targetTeamId())) {
            throw new IllegalArgumentException("Player already in this team");
        }

        Team currentTeam = teamRepository.findById(player.getTeam().getId())
                .orElseThrow(() -> new DataNotFoundException("Current team with id " + player.getTeam().getId() + " not found"));

        Team targetTeam = teamRepository.findById(transferRequestDto.targetTeamId())
                .orElseThrow(() -> new DataNotFoundException("Target team with id " + transferRequestDto.targetTeamId() + " not found"));

        BigDecimal totalPrice = PlayerPriceCalculatorUtil.calculateTotalPrice(player.getExperience(), player.getAge(), currentTeam.getCommission());
        if (targetTeam.getBalance().compareTo(totalPrice) < 0) {
            throw new IllegalArgumentException("Target team doesn't have enough balance");
        }

        targetTeam.setBalance(targetTeam.getBalance().subtract(totalPrice));
        currentTeam.setBalance(currentTeam.getBalance().add(totalPrice));
        player.setTeam(targetTeam);

        teamRepository.save(targetTeam);
        teamRepository.save(currentTeam);
        save(player);

        return playerMapper.toPlayerDto(player);
    }
}

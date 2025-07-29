package com.example.footballmanagerapp.common.service;

import com.example.footballmanagerapp.common.repositroy.TeamRepository;
import com.example.footballmanagerapp.dto.request.PlayerSaveDto;
import com.example.footballmanagerapp.dto.request.TransferRequestDto;
import com.example.footballmanagerapp.dto.resonse.PlayerDto;
import com.example.footballmanagerapp.entity.Player;
import com.example.footballmanagerapp.entity.Team;
import com.example.footballmanagerapp.exception.DataNotFoundException;
import com.example.footballmanagerapp.mapper.PlayerMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public Long createPlayer(PlayerSaveDto playerDto) {
        Team team = teamRepository.findById(playerDto.teamId())
                .orElseThrow(() -> new DataNotFoundException("Team with id " + playerDto.teamId() + " not found"));

        Player player = playerMapper.toEntity(playerDto);
        player.setTeam(team);

        return save(player).getId();
    }

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

    public void deletePlayer(Long playerId) {
        if (!existsById(playerId)) {
            throw new DataNotFoundException("Player with id " + playerId + " not found");
        }
        deleteById(playerId);
    }

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

        BigDecimal totalPrice = getTotalPrice(player, currentTeam, targetTeam);

        targetTeam.setBalance(targetTeam.getBalance().subtract(totalPrice));
        currentTeam.setBalance(currentTeam.getBalance().add(totalPrice));
        player.setTeam(targetTeam);

        teamRepository.save(targetTeam);
        teamRepository.save(currentTeam);
        save(player);

        return playerMapper.toPlayerDto(player);
    }


    private static BigDecimal getTotalPrice(Player player, Team currentTeam, Team targetTeam) {
        BigDecimal experience = BigDecimal.valueOf(player.getExperience());
        BigDecimal age = BigDecimal.valueOf(player.getAge());
        BigDecimal playerPrice = experience.multiply(BigDecimal.valueOf(100_000))
                .divide(age, 2, RoundingMode.HALF_UP);

        int commission = currentTeam.getCommission();
        BigDecimal commissionMultiplier = BigDecimal.valueOf(commission)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                .add(BigDecimal.ONE);

        BigDecimal totalPrice = playerPrice.multiply(commissionMultiplier).setScale(2, RoundingMode.HALF_UP);

        if (targetTeam.getBalance().compareTo(totalPrice) < 0) {
            throw new IllegalArgumentException("Target team doesn't have enough balance");
        }
        return totalPrice;
    }
}

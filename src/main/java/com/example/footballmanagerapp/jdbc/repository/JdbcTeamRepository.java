package com.example.footballmanagerapp.jdbc.repository;

import com.example.footballmanagerapp.common.repositroy.TeamRepository;
import com.example.footballmanagerapp.entity.Player;
import com.example.footballmanagerapp.entity.Team;
import com.example.footballmanagerapp.mapper.PlayerRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTeamRepository implements TeamRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PlayerRowMapper playerRowMapper;
    private final RowMapper<Team> teamRowMapper = (rs, rowNum) -> {
        Team team = new Team();
        team.setId(rs.getLong("id"));
        team.setName(rs.getString("name"));
        team.setCity(rs.getString("city"));
        team.setBalance(rs.getBigDecimal("balance"));
        team.setCommission(rs.getInt("commission"));
        return team;
    };

    @Override
    public Team save(Team team) {
        if (team.getId() == null) {
            // INSERT
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO team (name, city, balance, commission) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, team.getName());
                ps.setString(2, team.getCity());
                ps.setBigDecimal(3, team.getBalance());
                ps.setInt(4, team.getCommission());
                return ps;
            }, keyHolder);

            Number generatedId = keyHolder.getKey();
            if (generatedId != null) {
                team.setId(generatedId.longValue());
            }
        } else {
            // UPDATE
            jdbcTemplate.update(
                    "UPDATE team SET name = ?, city = ?, balance = ?, commission = ? WHERE id = ?",
                    team.getName(), team.getCity(), team.getBalance(), team.getCommission(), team.getId()
            );
        }
        return team;
    }

    @Override
    public Optional<Team> findById(Long id) {
        try {
            Team team = jdbcTemplate.queryForObject(
                    "SELECT * FROM team WHERE id = ?",
                    teamRowMapper,
                    id
            );
            if (team != null) {
                List<Player> players = jdbcTemplate.query(
                        "SELECT * FROM player WHERE team_id = ?",
                        (rs, rowNum) -> {
                            Player player = playerRowMapper.mapRow(rs, rowNum);
                            player.setTeam(team);
                            return player;
                        },
                        id
                );
                team.setPlayers(players);
            }
            return Optional.of(team);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Team> findAll() {
        List<Team> teams = jdbcTemplate.query("SELECT * FROM team", teamRowMapper);

        for (Team team : teams) {
            List<Player> players = jdbcTemplate.query(
                    "SELECT * FROM player WHERE team_id = ?",
                    (rs, rowNum) -> {
                        Player player = playerRowMapper.mapRow(rs, rowNum);
                        player.setTeam(team);
                        return player;
                    },
                    team.getId()
            );
            team.setPlayers(players);
        }

        return teams;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM team WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM player WHERE id = ?",
                Integer.class,
                id
        );
        return count != null && count > 0;
    }
}

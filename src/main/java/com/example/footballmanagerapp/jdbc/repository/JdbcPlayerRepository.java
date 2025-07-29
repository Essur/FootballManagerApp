package com.example.footballmanagerapp.jdbc.repository;


import com.example.footballmanagerapp.common.repositroy.PlayerRepository;
import com.example.footballmanagerapp.entity.Player;
import com.example.footballmanagerapp.entity.Team;
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
public class JdbcPlayerRepository implements PlayerRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Player> playerRowMapper = (rs, rowNum) -> {
        Player player = new Player();
        player.setId(rs.getLong("id"));
        player.setName(rs.getString("name"));
        player.setAge(rs.getInt("age"));
        player.setPosition(rs.getString("position"));
        player.setExperience(rs.getInt("experience"));

        Long teamId = rs.getLong("team_id");
        if (!rs.wasNull()) {
            Team team = new Team();
            team.setId(teamId);
            player.setTeam(team);
        }

        return player;
    };

    @Override
    public Player save(Player player) {
        if (player.getId() == null) {
            // INSERT
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO player (name, age, position, experience, team_id) VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, player.getName());
                ps.setInt(2, player.getAge());
                ps.setString(3, player.getPosition());
                ps.setInt(4, player.getExperience());

                if (player.getTeam() != null && player.getTeam().getId() != null) {
                    ps.setLong(6, player.getTeam().getId());
                } else {
                    ps.setNull(6, java.sql.Types.BIGINT);
                }

                return ps;
            }, keyHolder);

            Number generatedId = keyHolder.getKey();
            if (generatedId != null) {
                player.setId(generatedId.longValue());
            }
        } else {
            // UPDATE
            jdbcTemplate.update(
                    "UPDATE player SET name = ?, age = ?, position = ?, experience = ?, team_id = ? WHERE id = ?",
                    player.getName(),
                    player.getAge(),
                    player.getPosition(),
                    player.getExperience(),
                    (player.getTeam() != null ? player.getTeam().getId() : null),
                    player.getId()
            );
        }
        return player;
    }

    @Override
    public Optional<Player> findById(Long id) {
        try {
            Player player = jdbcTemplate.queryForObject(
                    "SELECT * FROM player WHERE id = ?",
                    playerRowMapper,
                    id
            );
            return Optional.ofNullable(player);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Player> findAll() {
        return jdbcTemplate.query("SELECT * FROM player", playerRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM player WHERE id = ?", id);
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

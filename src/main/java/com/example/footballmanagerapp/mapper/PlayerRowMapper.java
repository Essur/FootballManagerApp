package com.example.footballmanagerapp.mapper;

import com.example.footballmanagerapp.entity.Player;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PlayerRowMapper implements RowMapper<Player> {
    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        Player player = new Player();
        player.setId(rs.getLong("id"));
        player.setName(rs.getString("name"));
        player.setAge(rs.getInt("age"));
        player.setPosition(rs.getString("position"));
        player.setExperience(rs.getInt("experience"));
        return player;
    }
}

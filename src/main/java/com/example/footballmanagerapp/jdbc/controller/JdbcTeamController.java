package com.example.footballmanagerapp.jdbc.controller;

import com.example.footballmanagerapp.dto.resonse.TeamDto;
import com.example.footballmanagerapp.dto.request.TeamSaveDto;
import com.example.footballmanagerapp.jdbc.service.JdbcTeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jdbc/teams")
public class JdbcTeamController {
    private final JdbcTeamService teamService;

    @PostMapping
    public ResponseEntity<Long> createTeam(@Valid @RequestBody TeamSaveDto dto) {
        Long createdTeamId = teamService.createTeam(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeamId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id,
                                              @Valid @RequestBody TeamSaveDto dto) {
        TeamDto updatedTeam = teamService.updateTeam(dto, id);
        return ResponseEntity.ok(updatedTeam);
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        return ResponseEntity.ok(teamService.findAllTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.findTeamById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeamById(id);
        return ResponseEntity.noContent().build();
    }
}

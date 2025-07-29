package com.example.footballmanagerapp.jdbc.controller;

import com.example.footballmanagerapp.dto.resonse.TeamDto;
import com.example.footballmanagerapp.dto.request.TeamSaveDto;
import com.example.footballmanagerapp.jdbc.service.JdbcTeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jdbc/teams")
public class JdbcTeamController {
    private final JdbcTeamService teamService;

    @PostMapping
    public Long createTeam(@Valid @RequestBody TeamSaveDto dto) {
        return teamService.createTeam(dto);
    }

    @PutMapping("/{id}")
    public TeamDto updateTeam(@PathVariable Long id,
                              @Valid @RequestBody TeamSaveDto dto) {
        return teamService.updateTeam(dto,id);
    }

    @GetMapping
    public List<TeamDto> getTeams() {
        return teamService.findAllTeams();
    }

    @GetMapping("/{id}")
    public TeamDto getTeamById(@PathVariable Long id) {
        return teamService.findTeamById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeamById(id);
    }
}

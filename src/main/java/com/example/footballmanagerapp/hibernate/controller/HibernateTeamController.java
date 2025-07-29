package com.example.footballmanagerapp.hibernate.controller;

import com.example.footballmanagerapp.dto.resonse.TeamDto;
import com.example.footballmanagerapp.dto.request.TeamSaveDto;
import com.example.footballmanagerapp.hibernate.service.HibernateTeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hibernate/teams")
public class HibernateTeamController {
    private final HibernateTeamService teamService;

    @PostMapping
    public Long createTeam(@Valid @RequestBody TeamSaveDto dto) {
        return teamService.createTeam(dto);
    }

    @PutMapping("/{id}")
    public TeamDto updateTeam(@PathVariable Long id,
                              @Valid @RequestBody TeamSaveDto dto) {
        return teamService.updateTeam(dto, id);
    }

    @GetMapping
    public List<TeamDto> getAllTeams() {
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

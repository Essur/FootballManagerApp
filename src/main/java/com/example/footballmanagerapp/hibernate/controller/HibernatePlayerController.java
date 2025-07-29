package com.example.footballmanagerapp.hibernate.controller;

import com.example.footballmanagerapp.dto.request.PlayerSaveDto;
import com.example.footballmanagerapp.dto.request.TransferRequestDto;
import com.example.footballmanagerapp.dto.resonse.PlayerDto;
import com.example.footballmanagerapp.hibernate.service.HibernatePlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hibernate/players")
@RequiredArgsConstructor
public class HibernatePlayerController {
    private final HibernatePlayerService playerService;

    @PostMapping
    public Long createPlayer(@Valid @RequestBody PlayerSaveDto dto) {
        return playerService.createPlayer(dto);
    }

    @PostMapping("/{id}/transfer")
    public PlayerDto transferPlayer(@PathVariable Long id, @Valid @RequestBody TransferRequestDto transferRequestDto) {
        return playerService.transferPlayer(id, transferRequestDto);
    }

    @PutMapping("/{id}")
    public PlayerDto updatePlayer(@PathVariable Long id, @Valid @RequestBody PlayerSaveDto dto) {
        return playerService.updatePlayer(dto, id);
    }

    @GetMapping
    public List<PlayerDto> getPlayers() {
        return playerService.findAll();
    }

    @GetMapping("/{id}")
    public PlayerDto getPlayerById(@PathVariable Long id) {
        return playerService.findByIdDto(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }
}

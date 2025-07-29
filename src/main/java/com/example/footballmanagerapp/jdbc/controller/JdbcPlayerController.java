package com.example.footballmanagerapp.jdbc.controller;

import com.example.footballmanagerapp.dto.request.PlayerSaveDto;
import com.example.footballmanagerapp.dto.request.TransferRequestDto;
import com.example.footballmanagerapp.dto.resonse.PlayerDto;
import com.example.footballmanagerapp.jdbc.service.JdbcPlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jdbc/players")
@RequiredArgsConstructor
public class JdbcPlayerController {
    private final JdbcPlayerService jdbcPlayerService;

    @PostMapping
    public Long createPlayer(@Valid @RequestBody PlayerSaveDto dto) {
        return jdbcPlayerService.createPlayer(dto);
    }

    @PostMapping("/{id}/transfer")
    public PlayerDto transferPlayer(@PathVariable Long id, @Valid @RequestBody TransferRequestDto dto) {
        return jdbcPlayerService.transferPlayer(id, dto);
    }

    @PutMapping("/{id}")
    public PlayerDto updatePlayer(@PathVariable Long id,
                                  @Valid @RequestBody PlayerSaveDto dto) {
        return jdbcPlayerService.updatePlayer(dto, id);
    }

    @GetMapping
    public List<PlayerDto> getPlayers() {
        return jdbcPlayerService.findAll();
    }

    @GetMapping("/{id}")
    public PlayerDto getPlayer(@PathVariable Long id) {
        return jdbcPlayerService.findByIdDto(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlayer(@PathVariable Long id) {
        jdbcPlayerService.deletePlayer(id);
    }
}

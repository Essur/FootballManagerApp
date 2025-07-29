package com.example.footballmanagerapp.common.controller;

import com.example.footballmanagerapp.common.service.AbstractPlayerService;
import com.example.footballmanagerapp.dto.request.PlayerSaveDto;
import com.example.footballmanagerapp.dto.request.TransferRequestDto;
import com.example.footballmanagerapp.dto.resonse.PlayerDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
public abstract class AbstractPlayerController<S extends AbstractPlayerService> {
    protected final S playerService;

    protected AbstractPlayerController(S playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<Long> createPlayer(@Valid @RequestBody PlayerSaveDto dto) {
        Long id = playerService.createPlayer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/{id}/transfer")
    public ResponseEntity<PlayerDto> transferPlayer(@PathVariable Long id, @Valid @RequestBody TransferRequestDto transferRequestDto) {
        PlayerDto playerDto = playerService.transferPlayer(id, transferRequestDto);
        return ResponseEntity.ok(playerDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable Long id, @Valid @RequestBody PlayerSaveDto dto) {
        PlayerDto playerDto = playerService.updatePlayer(dto, id);
        return ResponseEntity.ok(playerDto);
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getPlayers() {
        List<PlayerDto> players = playerService.findAll();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable Long id) {
        PlayerDto playerDto = playerService.findByIdDto(id);
        return ResponseEntity.ok(playerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}

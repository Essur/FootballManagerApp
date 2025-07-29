package com.example.footballmanagerapp.jdbc.controller;

import com.example.footballmanagerapp.dto.request.PlayerSaveDto;
import com.example.footballmanagerapp.dto.request.TransferRequestDto;
import com.example.footballmanagerapp.dto.resonse.PlayerDto;
import com.example.footballmanagerapp.jdbc.service.JdbcPlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jdbc/players")
@RequiredArgsConstructor
public class JdbcPlayerController {
    private final JdbcPlayerService playerService;

    @PostMapping
    public ResponseEntity<Long> createPlayer(@Valid @RequestBody PlayerSaveDto dto) {
        Long createdPlayerId = playerService.createPlayer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayerId);
    }

    @PostMapping("/{id}/transfer")
    public ResponseEntity<PlayerDto> transferPlayer(@PathVariable Long id, @Valid @RequestBody TransferRequestDto transferRequestDto) {
        PlayerDto updatedPlayer = playerService.transferPlayer(id, transferRequestDto);
        return ResponseEntity.ok(updatedPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable Long id,
                                                  @Valid @RequestBody PlayerSaveDto dto) {
        PlayerDto updatedPlayer = playerService.updatePlayer(dto, id);
        return ResponseEntity.ok(updatedPlayer);
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getPlayers() {
        return ResponseEntity.ok(playerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.findByIdDto(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}

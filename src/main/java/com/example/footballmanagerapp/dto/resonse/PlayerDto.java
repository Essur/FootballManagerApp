package com.example.footballmanagerapp.dto.resonse;

public record PlayerDto(Long id, String name, int age, String position, int experience, Long teamId) {
}

package com.example.footballmanagerapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransferRequestDto (@NotNull @Min(1) Long targetTeamId) {}

package com.example.footballmanagerapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PlayerSaveDto(@NotEmpty String name,
                            @Min(18) @NotNull Integer age,
                            @NotEmpty String position,
                            @Min(0) @NotNull Integer experience,
                            @Min(1) @NotNull Long teamId) {
}

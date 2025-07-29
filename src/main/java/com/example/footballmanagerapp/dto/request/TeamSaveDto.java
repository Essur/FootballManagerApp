package com.example.footballmanagerapp.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TeamSaveDto(@NotEmpty String name,
                          @NotEmpty String city,
                          @Min(0) @NotNull BigDecimal balance,
                          @Min(0) @Max(10) @NotNull Integer commission) {
}

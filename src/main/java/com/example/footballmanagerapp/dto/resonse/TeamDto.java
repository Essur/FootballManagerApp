package com.example.footballmanagerapp.dto.resonse;

import java.util.List;

public record TeamDto(Long teamId,
                      String name,
                      String city,
                      String balance,
                      Integer commission,
                      List<PlayerDto> players) {
}

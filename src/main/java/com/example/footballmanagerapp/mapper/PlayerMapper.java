package com.example.footballmanagerapp.mapper;

import com.example.footballmanagerapp.dto.request.PlayerSaveDto;
import com.example.footballmanagerapp.dto.resonse.PlayerDto;
import com.example.footballmanagerapp.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    @Mapping(target = "team", ignore = true)
    Player toEntity(PlayerSaveDto playerDto);
    void updateEntity(PlayerSaveDto playerDto, @MappingTarget Player player);
    @Mapping(target = "teamId", source = "team.id")
    PlayerDto toPlayerDto(Player player);
}

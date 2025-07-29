package com.example.footballmanagerapp.mapper;

import com.example.footballmanagerapp.dto.resonse.TeamDto;
import com.example.footballmanagerapp.dto.request.TeamSaveDto;
import com.example.footballmanagerapp.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {PlayerMapper.class})
public interface TeamMapper {
    @Mapping(target = "teamId", source = "id")
    TeamDto toDto(Team team);
    Team toEntity(TeamSaveDto teamDto);
    void updateEntity(TeamSaveDto teamDto, @MappingTarget Team team);
}

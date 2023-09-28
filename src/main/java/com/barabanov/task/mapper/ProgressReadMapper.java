package com.barabanov.task.mapper;

import com.barabanov.task.dto.ProgressReadDto;
import com.barabanov.task.entity.Progress;


public class ProgressReadMapper implements Mapper<Progress, ProgressReadDto>
{
    @Override
    public ProgressReadDto mapFrom(Progress object)
    {
        Long playerId = object.getPlayer() != null ? object.getPlayer().getId() : null;
        return new ProgressReadDto(
                object.getId(),
                object.getResourceId(),
                object.getScore(),
                object.getMaxScore(),
                playerId
        );
    }
}

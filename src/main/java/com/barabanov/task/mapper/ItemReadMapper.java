package com.barabanov.task.mapper;

import com.barabanov.task.dto.ItemReadDto;
import com.barabanov.task.entity.Item;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ItemReadMapper implements Mapper<Item, ItemReadDto>
{

    @Override
    public ItemReadDto mapFrom(Item object)
    {
        Long playerId = object.getPlayer() != null ? object.getPlayer().getId() : null;
        return new ItemReadDto(
                object.getId(),
                object.getResourceId(),
                object.getCount(),
                object.getLevel(),
                playerId);

    }
}

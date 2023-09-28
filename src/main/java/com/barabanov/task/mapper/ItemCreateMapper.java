package com.barabanov.task.mapper;

import com.barabanov.task.dto.ItemCreateDto;
import com.barabanov.task.entity.Item;
import com.barabanov.task.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ItemCreateMapper implements Mapper<ItemCreateDto, Item>
{
    private final PlayerRepository playerRepository;

    @Override
    public Item mapFrom(ItemCreateDto object)
    {
        return Item.builder()
                .level(object.level())
                .count(object.count())
                .resourceId(object.resourceId())
                .player(playerRepository.findById(object.playerId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}

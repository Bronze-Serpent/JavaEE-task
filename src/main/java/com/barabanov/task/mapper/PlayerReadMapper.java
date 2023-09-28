package com.barabanov.task.mapper;

import com.barabanov.task.dto.PlayerReadDto;
import com.barabanov.task.entity.Player;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;


@RequiredArgsConstructor
public class PlayerReadMapper implements Mapper<Player, PlayerReadDto>
{
    private final ProgressReadMapper progressReadMapper;
    private final ItemReadMapper itemReadMapper;
    private final CurrencyReadMapper currencyReadMapper;


    @Override
    public PlayerReadDto mapFrom(Player object)
    {
        return new PlayerReadDto(
                object.getId(),
                object.getNickname(),
                object.getProgresses().stream().map(progressReadMapper::mapFrom).collect(Collectors.toList()),
                object.getItems().stream().map(itemReadMapper::mapFrom).collect(Collectors.toList()),
                object.getCurrencies().stream().map(currencyReadMapper::mapFrom).collect(Collectors.toList())
        );
    }
}

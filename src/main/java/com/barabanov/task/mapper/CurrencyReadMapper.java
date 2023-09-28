package com.barabanov.task.mapper;

import com.barabanov.task.dto.CurrencyReadDto;
import com.barabanov.task.entity.Currency;


public class CurrencyReadMapper implements Mapper<Currency, CurrencyReadDto>
{
    @Override
    public CurrencyReadDto mapFrom(Currency object)
    {
        Long playerId = object.getPlayer() != null ? object.getPlayer().getId() : null;
        return new CurrencyReadDto(
                object.getId(),
                object.getResourceId(),
                object.getName(),
                object.getCount(),
                playerId
        );
    }
}

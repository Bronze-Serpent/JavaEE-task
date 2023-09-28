package com.barabanov.task.mapper;

import com.barabanov.task.dto.CurrencyCreateDto;
import com.barabanov.task.entity.Currency;
import com.barabanov.task.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CurrencyCreateMapper implements Mapper<CurrencyCreateDto, Currency>
{
    private final PlayerRepository playerRepository;

    @Override
    public Currency mapFrom(CurrencyCreateDto object)
    {
        return Currency.builder()
                .resourceId(object.resourceId())
                .count(object.count())
                .name(object.name())
                .player(playerRepository.findById(object.playerId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}

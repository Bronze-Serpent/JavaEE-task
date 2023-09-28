package com.barabanov.task.service;

import com.barabanov.task.dto.CurrencyCreateDto;
import com.barabanov.task.dto.CurrencyReadDto;
import com.barabanov.task.mapper.CurrencyCreateMapper;
import com.barabanov.task.mapper.CurrencyReadMapper;
import com.barabanov.task.repository.CurrencyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
public class CurrencyService
{
    private final CurrencyRepository currencyRepository;

    private final CurrencyReadMapper currencyReadMapper;
    private final CurrencyCreateMapper currencyCreateMapper;

    private final Validator validator;


    @Transactional
    public final List<CurrencyReadDto> readAllForPlayer(Long playerId)
    {
        return currencyRepository.findAllWithPlayerId(playerId).stream()
                .map(currencyReadMapper::mapFrom).toList();
    }


    @Transactional
    public Long create(CurrencyCreateDto currencyCreateDto)
    {
        var validationResult = validator.validate(currencyCreateDto);
        if (!validationResult.isEmpty())
            throw new ConstraintViolationException(validationResult);

        return currencyRepository.save(currencyCreateMapper.mapFrom(currencyCreateDto)).getId();
    }


    @Transactional
    public final boolean delete(Long id)
    {
        var mayBeCurrency = currencyRepository.findById(id);
        mayBeCurrency.ifPresent(currencyRepository::delete);

        return mayBeCurrency.isPresent();
    }


    @Transactional
    public final Optional<CurrencyReadDto> findById(Long id)
    {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJakartaHintName(), currencyRepository.getGraphWithPlayer()
        );
        return currencyRepository.findById(id, properties).map(currencyReadMapper::mapFrom);
    }
}

package com.barabanov.task.service;

import com.barabanov.task.dto.ItemCreateDto;
import com.barabanov.task.dto.ItemReadDto;
import com.barabanov.task.mapper.ItemCreateMapper;
import com.barabanov.task.mapper.ItemReadMapper;
import com.barabanov.task.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
public class ItemService
{

    private final ItemRepository itemRepository;

    private final ItemReadMapper itemReadMapper;
    private final ItemCreateMapper itemCreateMapper;

    private final Validator validator;


    @Transactional
    public final List<ItemReadDto> readAllForPlayer(Long playerId)
    {
        return itemRepository.findAllWithPlayerId(playerId).stream()
                .map(itemReadMapper::mapFrom).toList();
    }


    @Transactional
    public Long create(ItemCreateDto itemCreateDto)
    {
        // validation createItemDto
        var validationResult = validator.validate(itemCreateDto);
        if (!validationResult.isEmpty())
            throw new ConstraintViolationException(validationResult);

        return itemRepository.save(itemCreateMapper.mapFrom(itemCreateDto)).getId();
    }


    @Transactional
    public Optional<ItemReadDto> findById(Long id)
    {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJakartaHintName(), itemRepository.getGraphWithPlayer()
        );
        return itemRepository.findById(id, properties).map(itemReadMapper::mapFrom);
    }


    @Transactional
    public boolean delete(Long id)
    {
        var mayBeItem = itemRepository.findById(id);
        mayBeItem.ifPresent(itemRepository::delete);

        return mayBeItem.isPresent();
    }
}

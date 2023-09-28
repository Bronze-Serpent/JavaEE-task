package com.barabanov.task.service;

import com.barabanov.task.dto.ProgressCreateDto;
import com.barabanov.task.dto.ProgressReadDto;
import com.barabanov.task.mapper.ProgressCreateMapper;
import com.barabanov.task.mapper.ProgressReadMapper;
import com.barabanov.task.repository.ProgressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
public class ProgressService
{
    private final ProgressRepository progressRepository;

    private final ProgressReadMapper progressReadMapper;
    private final ProgressCreateMapper progressCreateMapper;

    private final Validator validator;


    @Transactional
    public final List<ProgressReadDto> readAllForPlayer(Long playerId)
    {
        return progressRepository.findAllWithPlayerId(playerId).stream()
                .map(progressReadMapper::mapFrom).toList();
    }


    @Transactional
    public Optional<ProgressReadDto> readById(Long id)
    {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJakartaHintName(), progressRepository.getGraphWithPlayer()
        );
        return progressRepository.findById(id, properties)
                .map(progressReadMapper::mapFrom);
    }


    @Transactional
    public Long create(ProgressCreateDto progressCreateDto)
    {
        var validationResult = validator.validate(progressCreateDto);
        if(!validationResult.isEmpty())
            throw new ConstraintViolationException(validationResult);

        return progressRepository.save(progressCreateMapper.mapFrom(progressCreateDto)).getId();
    }


    @Transactional
    public boolean delete(Long id)
    {
        var mayBeProgress = progressRepository.findById(id);
        mayBeProgress.ifPresent(progressRepository::delete);

        return mayBeProgress.isPresent();
    }
}

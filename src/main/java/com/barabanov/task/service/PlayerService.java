package com.barabanov.task.service;

import com.barabanov.task.dto.PlayerCreateDto;
import com.barabanov.task.dto.PlayerReadDto;
import com.barabanov.task.entity.Player;
import com.barabanov.task.mapper.PlayerReadMapper;
import com.barabanov.task.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Optional;


@RequiredArgsConstructor
public class PlayerService
{
    private final PlayerRepository playerRepository;

    private final PlayerReadMapper playerReadMapper;

    private final Validator validator;


    @Transactional
    public Long create(PlayerCreateDto playerCreateDto)
    {
        var validationResult = validator.validate(playerCreateDto);
        if(!validationResult.isEmpty())
            throw new ConstraintViolationException(validationResult);

        return playerRepository.save(Player.builder()
                        .nickname(playerCreateDto.nickname())
                        .build())
                .getId();
    }


    @Transactional
    public boolean delete(Long id)
    {
        var mayBePlayer = playerRepository.findById(id);
        mayBePlayer.ifPresent(playerRepository::delete);

        return mayBePlayer.isPresent();
    }


    @Transactional
    public Optional<PlayerReadDto> findById(Long id)
    {
        return playerRepository.findById(id)
                .map(playerReadMapper::mapFrom);
    }

}

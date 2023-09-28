package com.barabanov.task.mapper;

import com.barabanov.task.dto.ProgressCreateDto;
import com.barabanov.task.entity.Progress;
import com.barabanov.task.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ProgressCreateMapper implements Mapper<ProgressCreateDto, Progress>
{
    private final PlayerRepository playerRepository;

    @Override
    public Progress mapFrom(ProgressCreateDto object)
    {
        return Progress.builder()
                .player(playerRepository.findById(object.playerId()).orElseThrow(IllegalArgumentException::new))
                .resourceId(object.resourceId())
                .score(object.score())
                .maxScore(object.maxScore())
                .build();
    }
}

package com.barabanov.task.dto;

import com.barabanov.task.validation.ValidPlayerId;

import javax.validation.constraints.NotNull;

public record ItemCreateDto(
                            @ValidPlayerId
                            Long playerId,
                            @NotNull
                            Integer resourceId,
                            @NotNull
                            Integer count,
                            @NotNull
                            Integer level
){ }

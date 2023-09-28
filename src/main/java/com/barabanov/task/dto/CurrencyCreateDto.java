package com.barabanov.task.dto;

import com.barabanov.task.validation.ValidPlayerId;

import javax.validation.constraints.NotNull;

public record CurrencyCreateDto(
                                 @ValidPlayerId
                                 Long playerId,
                                 @NotNull
                                 String name,
                                 @NotNull
                                 Integer count,
                                 @NotNull
                                 Integer resourceId
) { }

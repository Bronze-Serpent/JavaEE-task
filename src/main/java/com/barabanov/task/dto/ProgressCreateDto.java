package com.barabanov.task.dto;

import com.barabanov.task.validation.ValidPlayerId;

import javax.validation.constraints.NotNull;


public record ProgressCreateDto(
                                  @ValidPlayerId
                                  Long playerId,
                                  @NotNull
                                  Integer resourceId,
                                  @NotNull
                                  Integer score,
                                  @NotNull
                                  Integer maxScore
) {
}

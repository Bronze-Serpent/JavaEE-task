package com.barabanov.task.dto;

public record ProgressReadDto(
                                Long id,
                                Integer resourceId,
                                Integer score,
                                Integer maxScore,
                                Long playerId
){ }

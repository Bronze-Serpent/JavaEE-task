package com.barabanov.task.dto;


public record ItemReadDto(
                            Long id,
                            Integer resourceId,
                            Integer count,
                            Integer level,
                            Long playerId
){ }

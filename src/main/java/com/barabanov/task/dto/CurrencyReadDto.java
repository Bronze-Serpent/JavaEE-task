package com.barabanov.task.dto;


public record CurrencyReadDto(
                                Long id,
                                Integer resourceId,
                                String name,
                                Integer count,
                                Long playerId
){}

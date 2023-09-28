package com.barabanov.task.dto;

import java.util.List;


public record PlayerReadDto(
                            Long id,
                            String nickname,
                            List<ProgressReadDto> progresses,
                            List<ItemReadDto> items,
                            List<CurrencyReadDto> currencies
) {}

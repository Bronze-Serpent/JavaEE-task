package com.barabanov.task.dto;

import com.barabanov.task.validation.ValidNickname;

public record PlayerCreateDto(
                                @ValidNickname
                                String nickname
) {
}

package com.barabanov.task.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NicknameValidator implements ConstraintValidator<ValidNickname, String>
{
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        // TODO: 28.09.2023 Нужен application context + DI для получения playerRepository внутри с EntityManager
        return value != null;
    }
}

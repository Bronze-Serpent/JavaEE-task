package com.barabanov.task.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PlayerIdValidator implements ConstraintValidator<ValidPlayerId, Long>
{

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context)
    {
        // TODO: 28.09.2023 Нужен application context + DI для получения playerRepository внутри с EntityManager
        return value != null;
    }

}

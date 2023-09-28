package com.barabanov.task.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PlayerIdValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPlayerId
{
    String message() default "A player with this id must exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
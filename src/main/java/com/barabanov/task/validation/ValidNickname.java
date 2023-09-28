package com.barabanov.task.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = NicknameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNickname
{
    String message() default "Nickname must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

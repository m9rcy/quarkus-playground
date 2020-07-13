package io.m9rcy.playground.web.validation.constraint;

import io.m9rcy.playground.web.validation.validator.FieldMustBeValidEnumValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldMustBeValidEnumValueValidator.class)
@Documented
public @interface FieldMustBeValidEnumValue {
    Class<? extends Enum<?>> enumClazz();

    String message() default "Value is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

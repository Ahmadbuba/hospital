package com.system.hospital.constraint;

import com.system.hospital.validator.ValueOfEnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValueOfEnumValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueOfEnum {
    /**
     * @return class containing enum values to which this String should match
     */
    Class<? extends Enum<?>> enumClass();;

    /**
     * @return the error message template
     */

    String message() default "must be any of enum {enumClass}";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default {};
}

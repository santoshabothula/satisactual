package com.datawise.satisactual.custom.validations.annotation;

import com.datawise.satisactual.custom.validations.validator.NumberTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({
        ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberTypeValidator.class)
@Documented
public @interface NumberType {
    String message() default "Communication preference must be email or mobile.";
    Class<?>[] groups() default {};
    Class<?extends Payload> [] payload() default {};
}

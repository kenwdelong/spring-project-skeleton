package com.kendelong.web.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = BigDecimalRangeValidator.class)
@Documented
public @interface BigDecimalRange 
{
    public String message() default "{java.math.BigDecimal.range.error}";
    public Class<?>[] groups() default {};

    double min() default 0;
    double max() default Double.MAX_VALUE;
}
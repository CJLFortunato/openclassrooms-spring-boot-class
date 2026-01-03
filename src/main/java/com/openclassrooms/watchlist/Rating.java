package com.openclassrooms.watchlist;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.RetentionPolicy;

@Target ({ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy= RatingValidator.class)
public @interface Rating {
    String message() default "Rating should be a number between 0-10";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
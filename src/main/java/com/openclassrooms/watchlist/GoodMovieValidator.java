package com.openclassrooms.watchlist;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GoodMovieValidator implements ConstraintValidator<GoodMovie, WatchlistItem>{

    @Override
    public boolean isValid(WatchlistItem value, ConstraintValidatorContext context) {
        return !(Double.valueOf(value.getRating()) >= 8 && "Low".equals(value.getPriority().trim()));
    }

}

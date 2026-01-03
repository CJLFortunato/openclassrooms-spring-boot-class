package com.openclassrooms.watchlist;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<Priority, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals("High") || value.equals("Medium") || value.equals("Low");
    }

}

package com.crocobet.usermanagement.exception.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    private static final int MIN_LENGTH = 8;
    public static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+-=[]|,./?><";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password.length() < MIN_LENGTH)
            return false;

        boolean containsUpperCase = false;
        boolean containsDigit = false;
        boolean containsSpecialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                containsUpperCase = true;
            } else if (Character.isDigit(c)) {
                containsDigit = true;
            } else if (SPECIAL_CHARACTERS.indexOf(c) >= 0) {
                containsSpecialChar = true;
            }
        }

        return containsUpperCase && containsDigit && containsSpecialChar;
    }
}

package com.crocobet.usermanagement.util;

import jakarta.validation.ConstraintViolation;

import java.util.Objects;
import java.util.stream.StreamSupport;

public final class JakartaValidationUtil {
    private JakartaValidationUtil() {
    }

    /**
     * Extracts and returns the field name from a given ConstraintViolation.
     *
     * @param violation the ConstraintViolation instance from which the field name will be extracted.
     * @return the field name from the violation.
     */
    public static String extractFieldNameFrom(final ConstraintViolation<?> violation) {
        return Objects.requireNonNull(StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                        .reduce((first, second) -> second)
                        .orElse(null))
                .toString();
    }

}
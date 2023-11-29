package com.crocobet.usermanagement.exception.model;

import com.crocobet.usermanagement.exception.InvalidRequestException;

import java.util.Set;

public final class ExceptionFactory {
    public static InvalidRequestException createInvalidRequestException(String field, String violation) {
        Set<ConstraintViolationField> violationFields = Set.of(new ConstraintViolationField(field, violation));
        return new InvalidRequestException(violationFields);
    }
}

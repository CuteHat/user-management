package com.crocobet.usermanagement.exception.model;

import com.crocobet.usermanagement.exception.InvalidRequestException;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ValidationErrorResponse extends GeneralErrorResponse {
    private Set<ConstraintViolationField> validationErrors;

    public ValidationErrorResponse(final Set<ConstraintViolationField> validationErrors) {
        super("request failed with validation errors");
        this.validationErrors = validationErrors;
    }

    public static ValidationErrorResponse from(final Set<ConstraintViolation<?>> constraintViolations) {
        Set<ConstraintViolationField> validationErrors = constraintViolations.stream()
                .map(ConstraintViolationField::from)
                .collect(Collectors.toSet());
        return new ValidationErrorResponse(validationErrors);
    }

    public static ValidationErrorResponse from(BindingResult bindingResult) {
        Set<ConstraintViolationField> violations = bindingResult.getAllErrors()
                .stream()
                .map(ConstraintViolationField::from)
                .collect(Collectors.toSet());
        return new ValidationErrorResponse(violations);
    }

    public static ValidationErrorResponse from(MissingRequestHeaderException missingRequestHeaderException) {
        return new ValidationErrorResponse(Set.of(ConstraintViolationField.from(missingRequestHeaderException)));
    }

    public static ValidationErrorResponse from(MissingServletRequestParameterException missingRequestHeaderException) {
        return new ValidationErrorResponse(Set.of(ConstraintViolationField.from(missingRequestHeaderException)));
    }

    public static ValidationErrorResponse from(InvalidRequestException invalidRequestException) {
        return new ValidationErrorResponse(invalidRequestException.getViolations());
    }
}

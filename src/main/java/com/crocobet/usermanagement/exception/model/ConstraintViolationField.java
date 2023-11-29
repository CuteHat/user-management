package com.crocobet.usermanagement.exception.model;

import com.crocobet.usermanagement.util.JakartaValidationUtil;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;

@Data
@AllArgsConstructor
public class ConstraintViolationField {
    private String name;
    private String violation;

    public static ConstraintViolationField from(final ConstraintViolation<?> constraintViolation) {
        return new ConstraintViolationField(
                JakartaValidationUtil.extractFieldNameFrom(constraintViolation),
                constraintViolation.getMessage()
        );
    }

    public static ConstraintViolationField from(final ObjectError fieldError) {
        String fieldName = ((FieldError) fieldError).getField();
        String errorMessage = fieldError.getDefaultMessage();
        return new ConstraintViolationField(fieldName, errorMessage);
    }

    public static ConstraintViolationField from(final MissingRequestHeaderException ex) {
        return new ConstraintViolationField(ex.getHeaderName(), ex.getMessage());
    }

    public static ConstraintViolationField from(final MissingServletRequestParameterException ex) {
        return new ConstraintViolationField(ex.getParameterName(), ex.getMessage());
    }

}

package com.crocobet.usermanagement.exception;

import com.crocobet.usermanagement.exception.model.ConstraintViolationField;
import com.crocobet.usermanagement.util.ExceptionUtil;
import lombok.Getter;

import java.util.Set;

@Getter
public class InvalidRequestException extends RuntimeException {
    private final Set<ConstraintViolationField> violations;

    public InvalidRequestException(Set<ConstraintViolationField> violations) {
        super(ExceptionUtil.buildMessageFrom(violations));
        this.violations = violations;
    }
}

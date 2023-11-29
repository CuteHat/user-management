package com.crocobet.usermanagement.util;

import com.crocobet.usermanagement.exception.NotFoundException;
import com.crocobet.usermanagement.exception.model.ConstraintViolationField;

import java.util.Set;

public final class ExceptionUtil {
    public static String buildMessageFrom(Set<ConstraintViolationField> violations) {
        if (violations == null) return null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("encountered validation errors -> [");
        for (ConstraintViolationField violationField : violations) {
            stringBuilder
                    .append("(field:")
                    .append(violationField.getName())
                    .append(", violation:")
                    .append(violationField.getViolation())
                    .append("),");
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String buildMessageFrom(NotFoundException ex) {
        return buildMessageFrom(ex.getResource(), ex.getIdentifier());
    }

    public static String buildMessageFrom(String resource, String identifier) {
        return String.format("%s with identifier %s not found", resource, identifier);
    }
}

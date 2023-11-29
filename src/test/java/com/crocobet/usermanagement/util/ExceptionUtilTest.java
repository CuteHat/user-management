package com.crocobet.usermanagement.util;

import com.crocobet.usermanagement.exception.NotFoundException;
import com.crocobet.usermanagement.exception.model.ConstraintViolationField;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExceptionUtilTest {

    @Test
    void testBuildMessageFromWithEmptySet() {
        Set<ConstraintViolationField> violations = new HashSet<>();
        assertEquals("encountered validation errors -> []", ExceptionUtil.buildMessageFrom(violations));
    }

    @Test
    void testBuildMessageFromWithOneViolation() {
        Set<ConstraintViolationField> violations = new HashSet<>();
        violations.add(new ConstraintViolationField("fieldName", "violationMessage"));
        assertEquals("encountered validation errors -> [(field:fieldName, violation:violationMessage)]", ExceptionUtil.buildMessageFrom(violations));
    }

    @Test
    void testBuildMessageFromWithMultipleViolations() {
        Set<ConstraintViolationField> violations = new LinkedHashSet<>();
        violations.add(new ConstraintViolationField("field1", "violation1"));
        violations.add(new ConstraintViolationField("field2", "violation2"));
        String expectedMessage = "encountered validation errors -> [(field:field1, violation:violation1),(field:field2, violation:violation2)]";
        assertEquals(expectedMessage, ExceptionUtil.buildMessageFrom(violations));
    }

    @Test
    void buildMessageFromConstraintViolations() {
        Set<ConstraintViolationField> violations = new LinkedHashSet<>();
        violations.add(new ConstraintViolationField("field1", "violation1"));
        violations.add(new ConstraintViolationField("field2", "violation2"));

        String message = ExceptionUtil.buildMessageFrom(violations);
        assertEquals("encountered validation errors -> [(field:field1, violation:violation1),(field:field2, violation:violation2)]", message);
    }

    @Test
    void buildMessageFromNullConstraintViolations() {
        String message = ExceptionUtil.buildMessageFrom((Set<ConstraintViolationField>) null);
        assertNull(message);
    }

    @Test
    void buildMessageFromNotFoundException() {
        NotFoundException ex = new NotFoundException("user", "123");
        String message = ExceptionUtil.buildMessageFrom(ex);
        assertEquals("user with identifier 123 not found", message);
    }

}
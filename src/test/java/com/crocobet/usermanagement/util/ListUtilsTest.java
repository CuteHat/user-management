package com.crocobet.usermanagement.util;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListUtilsTest {

    @Test
    void listOfOneOrNullWithNullItem() {
        List<Object> result = ListUtils.listOfOneOrNull(null);
        assertTrue(result.isEmpty(), "The list should be empty for a null item");
    }

    @Test
    void listOfOneOrNullWithNonNullItem() {
        String testItem = "test";
        List<String> result = ListUtils.listOfOneOrNull(testItem);
        assertEquals(Collections.singletonList(testItem), result, "The list should contain the provided item");
    }
}
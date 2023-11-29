package com.crocobet.usermanagement.util;

import java.util.Collections;
import java.util.List;

public final class ListUtils {
    public static <T> List<T> listOfOneOrNull(T item) {
        return (item != null) ? Collections.singletonList(item) : Collections.emptyList();
    }
}

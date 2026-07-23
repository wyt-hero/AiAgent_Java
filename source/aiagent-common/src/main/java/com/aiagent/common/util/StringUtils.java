package com.aiagent.common.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * String utility methods.
 * Supplements Hutool with framework-specific operations.
 */
public final class StringUtils {

    private StringUtils() {
        // Utility class
    }

    /**
     * Check if a string is null or empty.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Check if a string is null, empty, or contains only whitespace.
     */
    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    /**
     * Check if a string is not blank.
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Return the string or a default value if blank.
     */
    public static String defaultIfBlank(String str, String defaultValue) {
        return isBlank(str) ? defaultValue : str;
    }

    /**
     * Truncate a string to the specified max length, appending "..." if truncated.
     */
    public static String truncate(String str, int maxLength) {
        Objects.requireNonNull(str, "str must not be null");
        if (maxLength < 3) {
            throw new IllegalArgumentException("maxLength must be >= 3");
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }

    /**
     * Check if a collection is null or empty.
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Check if a map is null or empty.
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}

package com.aiagent.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StringUtils Tests")
class StringUtilsTest {

    @Test
    @DisplayName("should_ReturnTrue_When_StringIsNullOrEmpty")
    void should_ReturnTrue_When_StringIsNullOrEmpty() {
        assertTrue(StringUtils.isNullOrEmpty((String) null));
        assertTrue(StringUtils.isNullOrEmpty(""));
        assertFalse(StringUtils.isNullOrEmpty("hello"));
        assertFalse(StringUtils.isNullOrEmpty("  "));
    }

    @Test
    @DisplayName("should_ReturnTrue_When_StringIsBlank")
    void should_ReturnTrue_When_StringIsBlank() {
        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank("   "));
        assertFalse(StringUtils.isBlank("hello"));
    }

    @Test
    @DisplayName("should_TruncateString_When_ExceedsMaxLength")
    void should_TruncateString_When_ExceedsMaxLength() {
        assertEquals("hello", StringUtils.truncate("hello", 10));
        assertEquals("he...", StringUtils.truncate("hello world", 5));
    }

    @Test
    @DisplayName("should_ThrowException_When_MaxLengthTooSmall")
    void should_ThrowException_When_MaxLengthTooSmall() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.truncate("test", 2));
    }

    @Test
    @DisplayName("should_ReturnDefault_When_Blank")
    void should_ReturnDefault_When_Blank() {
        assertEquals("default", StringUtils.defaultIfBlank(null, "default"));
        assertEquals("default", StringUtils.defaultIfBlank("", "default"));
        assertEquals("default", StringUtils.defaultIfBlank("  ", "default"));
        assertEquals("value", StringUtils.defaultIfBlank("value", "default"));
    }

    @Test
    @DisplayName("should_CheckCollectionNullOrEmpty")
    void should_CheckCollectionNullOrEmpty() {
        assertTrue(StringUtils.isNullOrEmpty((Collection<?>) null));
        assertTrue(StringUtils.isNullOrEmpty(List.of()));
        assertFalse(StringUtils.isNullOrEmpty(List.of("a")));
    }

    @Test
    @DisplayName("should_CheckMapNullOrEmpty")
    void should_CheckMapNullOrEmpty() {
        assertTrue(StringUtils.isNullOrEmpty((Map<?, ?>) null));
        assertTrue(StringUtils.isNullOrEmpty(Map.of()));
        assertFalse(StringUtils.isNullOrEmpty(Map.of("key", "value")));
    }
}

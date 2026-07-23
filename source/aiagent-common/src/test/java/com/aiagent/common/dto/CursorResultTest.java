package com.aiagent.common.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CursorResult Tests")
class CursorResultTest {

    @Test
    @DisplayName("should_CreateCursorResult_When_WithAllFields")
    void should_CreateCursorResult_When_WithAllFields() {
        List<String> items = List.of("a", "b", "c");

        CursorResult<String> result = CursorResult.of(items, "cursor-next", "cursor-prev", 3, true);

        assertEquals(3, result.itemCount());
        assertEquals("cursor-next", result.nextCursor());
        assertEquals("cursor-prev", result.previousCursor());
        assertEquals(3, result.size());
        assertTrue(result.hasMore());
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("should_CreateForwardCursor_When_OnlyNextCursor")
    void should_CreateForwardCursor_When_OnlyNextCursor() {
        List<String> items = List.of("x", "y");

        CursorResult<String> result = CursorResult.forward(items, "next-cursor", 2, true);

        assertEquals("next-cursor", result.nextCursor());
        assertNull(result.previousCursor());
        assertTrue(result.hasMore());
    }

    @Test
    @DisplayName("should_CreateEmptyCursorResult_When_NoItems")
    void should_CreateEmptyCursorResult_When_NoItems() {
        CursorResult<String> result = CursorResult.empty(10);

        assertTrue(result.isEmpty());
        assertEquals(0, result.itemCount());
        assertNull(result.nextCursor());
        assertNull(result.previousCursor());
        assertFalse(result.hasMore());
    }

    @Test
    @DisplayName("should_ThrowException_When_ItemsIsNull")
    void should_ThrowException_When_ItemsIsNull() {
        assertThrows(NullPointerException.class, () ->
                new CursorResult<>(null, null, null, 10, false)
        );
    }

    @Test
    @DisplayName("should_ThrowException_When_SizeIsZero")
    void should_ThrowException_When_SizeIsZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new CursorResult<>(List.of(), null, null, 0, false)
        );
    }

    @Test
    @DisplayName("should_ReturnImmutableItems_When_Accessed")
    void should_ReturnImmutableItems_When_Accessed() {
        CursorResult<String> result = CursorResult.of(List.of("a"), null, null, 1, false);

        assertThrows(UnsupportedOperationException.class, () ->
                result.items().add("b")
        );
    }

    @Test
    @DisplayName("should_ReturnHasMoreFalse_When_LastPage")
    void should_ReturnHasMoreFalse_When_LastPage() {
        CursorResult<String> result = CursorResult.forward(List.of("last"), null, 10, false);

        assertFalse(result.hasMore());
    }
}

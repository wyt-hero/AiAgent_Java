package com.aiagent.common.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PageResult DTO Tests")
class PageResultTest {

    @Test
    @DisplayName("should_CreatePageResult_When_ValidInput")
    void should_CreatePageResult_When_ValidInput() {
        PageResult<String> result = PageResult.of(List.of("a", "b", "c"), 10, 1, 3);

        assertEquals(3, result.items().size());
        assertEquals(10, result.total());
        assertEquals(1, result.page());
        assertEquals(3, result.size());
        assertEquals(4, result.totalPages());
        assertTrue(result.hasNext());
        assertFalse(result.hasPrevious());
    }

    @Test
    @DisplayName("should_ReturnEmptyPage_When_Empty")
    void should_ReturnEmptyPage_When_Empty() {
        PageResult<String> result = PageResult.empty(1, 20);

        assertTrue(result.items().isEmpty());
        assertEquals(0, result.total());
        assertEquals(0, result.totalPages());
        assertFalse(result.hasNext());
        assertFalse(result.hasPrevious());
    }

    @Test
    @DisplayName("should_ThrowException_When_InvalidPage")
    void should_ThrowException_When_InvalidPage() {
        assertThrows(IllegalArgumentException.class, () -> PageResult.of(List.of(), 0, 0, 10));
    }

    @Test
    @DisplayName("should_ThrowException_When_InvalidSize")
    void should_ThrowException_When_InvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> PageResult.of(List.of(), 0, 1, 0));
    }

    @Test
    @DisplayName("should_CalculateTotalPages_When_PartialLastPage")
    void should_CalculateTotalPages_When_PartialLastPage() {
        PageResult<String> result = PageResult.of(List.of("a"), 5, 2, 3);

        assertEquals(2, result.totalPages());
        assertFalse(result.hasNext());
        assertTrue(result.hasPrevious());
    }
}

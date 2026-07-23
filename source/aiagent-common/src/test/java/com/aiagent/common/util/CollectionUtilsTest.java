package com.aiagent.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CollectionUtils Tests")
class CollectionUtilsTest {

    @Test
    @DisplayName("should_ReturnTrue_When_CollectionIsNullOrEmpty")
    void should_ReturnTrue_When_CollectionIsNullOrEmpty() {
        assertTrue(CollectionUtils.isNullOrEmpty(null));
        assertTrue(CollectionUtils.isNullOrEmpty(List.of()));
        assertFalse(CollectionUtils.isNullOrEmpty(List.of("a")));
    }

    @Test
    @DisplayName("should_ReturnUnmodifiableList_When_NullSafe")
    void should_ReturnUnmodifiableList_When_NullSafe() {
        List<String> result = CollectionUtils.nullSafe(null);
        assertTrue(result.isEmpty());

        List<String> input = List.of("a", "b");
        List<String> safeResult = CollectionUtils.nullSafe(input);
        assertEquals(2, safeResult.size());
        assertThrows(UnsupportedOperationException.class, () -> safeResult.add("c"));
    }

    @Test
    @DisplayName("should_MapCollection_When_ValidInput")
    void should_MapCollection_When_ValidInput() {
        List<Integer> result = CollectionUtils.map(List.of("a", "bb", "ccc"), String::length);

        assertEquals(List.of(1, 2, 3), result);
    }

    @Test
    @DisplayName("should_PartitionList_When_ValidInput")
    void should_PartitionList_When_ValidInput() {
        List<List<Integer>> partitions = CollectionUtils.partition(List.of(1, 2, 3, 4, 5), 2);

        assertEquals(3, partitions.size());
        assertEquals(List.of(1, 2), partitions.get(0));
        assertEquals(List.of(3, 4), partitions.get(1));
        assertEquals(List.of(5), partitions.get(2));
    }

    @Test
    @DisplayName("should_ReturnFirstOrNull_When_ListProvided")
    void should_ReturnFirstOrNull_When_ListProvided() {
        assertEquals("a", CollectionUtils.firstOrNull(List.of("a", "b")));
        assertNull(CollectionUtils.firstOrNull(null));
        assertNull(CollectionUtils.firstOrNull(List.of()));
    }
}

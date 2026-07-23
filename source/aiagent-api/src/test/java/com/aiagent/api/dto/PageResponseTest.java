package com.aiagent.api.dto;

import com.aiagent.common.dto.PageResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PageResponse Tests")
class PageResponseTest {

    @Test
    @DisplayName("should_CreatePageResponse_When_FromPageResult")
    void should_CreatePageResponse_When_FromPageResult() {
        PageResult<String> pageResult = PageResult.of(List.of("a", "b"), 10, 1, 2);

        PageResponse<String> response = PageResponse.from(pageResult);

        assertEquals(2, response.content().size());
        assertEquals(1, response.page());
        assertEquals(2, response.size());
        assertEquals(10, response.totalElements());
        assertEquals(5, response.totalPages());
        assertTrue(response.hasNext());
        assertFalse(response.hasPrevious());
    }

    @Test
    @DisplayName("should_CreatePageResponse_When_OfWithExplicitParams")
    void should_CreatePageResponse_When_OfWithExplicitParams() {
        PageResponse<String> response = PageResponse.of(List.of("x"), 3, 10, 25);

        assertEquals(1, response.content().size());
        assertEquals(3, response.page());
        assertEquals(10, response.size());
        assertEquals(25, response.totalElements());
        assertEquals(3, response.totalPages());
        assertFalse(response.hasNext());
        assertTrue(response.hasPrevious());
    }

    @Test
    @DisplayName("should_CreateEmptyPageResponse_When_Empty")
    void should_CreateEmptyPageResponse_When_Empty() {
        PageResponse<String> response = PageResponse.empty(1, 20);

        assertTrue(response.content().isEmpty());
        assertEquals(0, response.totalElements());
        assertFalse(response.hasNext());
        assertFalse(response.hasPrevious());
    }

    @Test
    @DisplayName("should_ThrowException_When_ContentIsNull")
    void should_ThrowException_When_ContentIsNull() {
        assertThrows(NullPointerException.class, () ->
                new PageResponse<>(null, 1, 10, 0, 0, false, false)
        );
    }

    @Test
    @DisplayName("should_ReturnImmutableContent_When_Accessed")
    void should_ReturnImmutableContent_When_Accessed() {
        PageResponse<String> response = PageResponse.of(List.of("a"), 1, 10, 1);

        assertThrows(UnsupportedOperationException.class, () ->
                response.content().add("b")
        );
    }

    @Nested
    @DisplayName("Pagination metadata")
    class PaginationMetadataTests {

        @Test
        @DisplayName("should_CalculateCorrectTotalPages_When_TotalNotDivisible")
        void should_CalculateCorrectTotalPages_When_TotalNotDivisible() {
            PageResponse<String> response = PageResponse.of(List.of("a"), 1, 3, 7);

            assertEquals(3, response.totalPages());
        }

        @Test
        @DisplayName("should_HaveNextFalse_When_LastPage")
        void should_HaveNextFalse_When_LastPage() {
            PageResponse<String> response = PageResponse.of(List.of("a"), 3, 3, 7);

            assertFalse(response.hasNext());
        }

        @Test
        @DisplayName("should_HavePreviousTrue_When_NotFirstPage")
        void should_HavePreviousTrue_When_NotFirstPage() {
            PageResponse<String> response = PageResponse.of(List.of("a"), 2, 3, 7);

            assertTrue(response.hasPrevious());
        }
    }
}

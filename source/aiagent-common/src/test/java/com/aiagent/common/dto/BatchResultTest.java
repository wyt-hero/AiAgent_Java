package com.aiagent.common.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BatchResult Tests")
class BatchResultTest {

    @Test
    @DisplayName("should_CreateBatchResult_When_MixedSuccessAndErrors")
    void should_CreateBatchResult_When_MixedSuccessAndErrors() {
        List<BatchResult.BatchError> errors = List.of(
                BatchResult.BatchError.of(1, "ERR_001", "item 1 failed")
        );

        BatchResult<String> result = BatchResult.of(
                List.of("item0", "item2"), errors, 3
        );

        assertEquals(2, result.successCount());
        assertEquals(1, result.errorCount());
        assertEquals(3, result.total());
        assertTrue(result.hasErrors());
        assertFalse(result.isAllSuccess());
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("should_CreateAllSuccess_When_NoErrors")
    void should_CreateAllSuccess_When_NoErrors() {
        BatchResult<String> result = BatchResult.allSuccess(List.of("a", "b", "c"));

        assertEquals(3, result.successCount());
        assertEquals(0, result.errorCount());
        assertEquals(3, result.total());
        assertTrue(result.isAllSuccess());
        assertFalse(result.hasErrors());
    }

    @Test
    @DisplayName("should_CreateEmpty_When_NoItems")
    void should_CreateEmpty_When_NoItems() {
        BatchResult<String> result = BatchResult.empty();

        assertEquals(0, result.total());
        assertEquals(0, result.successCount());
        assertEquals(0, result.errorCount());
        assertTrue(result.isEmpty());
        assertFalse(result.hasErrors());
    }

    @Test
    @DisplayName("should_ThrowException_When_SuccessesIsNull")
    void should_ThrowException_When_SuccessesIsNull() {
        assertThrows(NullPointerException.class, () ->
                new BatchResult<>(null, List.of(), 0, 0, 0)
        );
    }

    @Test
    @DisplayName("should_ThrowException_When_ErrorsIsNull")
    void should_ThrowException_When_ErrorsIsNull() {
        assertThrows(NullPointerException.class, () ->
                new BatchResult<>(List.of(), null, 0, 0, 0)
        );
    }

    @Test
    @DisplayName("should_ThrowException_When_TotalIsNegative")
    void should_ThrowException_When_TotalIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new BatchResult<>(List.of(), List.of(), -1, 0, 0)
        );
    }

    @Test
    @DisplayName("should_ReturnImmutableSuccesses_When_Accessed")
    void should_ReturnImmutableSuccesses_When_Accessed() {
        BatchResult<String> result = BatchResult.allSuccess(List.of("a"));

        assertThrows(UnsupportedOperationException.class, () ->
                result.successes().add("b")
        );
    }

    @Test
    @DisplayName("should_ReturnImmutableErrors_When_Accessed")
    void should_ReturnImmutableErrors_When_Accessed() {
        BatchResult<String> result = BatchResult.of(
                List.of(), List.of(BatchResult.BatchError.of(0, "E", "err")), 1
        );

        assertThrows(UnsupportedOperationException.class, () ->
                result.errors().add(BatchResult.BatchError.of(1, "E2", "err2"))
        );
    }

    @Nested
    @DisplayName("BatchError")
    class BatchErrorTests {

        @Test
        @DisplayName("should_CreateBatchError_When_ValidParams")
        void should_CreateBatchError_When_ValidParams() {
            BatchResult.BatchError error = BatchResult.BatchError.of(0, "CODE_001", "failed");

            assertEquals(0, error.index());
            assertEquals("CODE_001", error.code());
            assertEquals("failed", error.message());
        }

        @Test
        @DisplayName("should_ThrowException_When_IndexIsNegative")
        void should_ThrowException_When_IndexIsNegative() {
            assertThrows(IllegalArgumentException.class, () ->
                    BatchResult.BatchError.of(-1, "CODE", "msg")
            );
        }

        @Test
        @DisplayName("should_ThrowException_When_CodeIsNull")
        void should_ThrowException_When_CodeIsNull() {
            assertThrows(NullPointerException.class, () ->
                    BatchResult.BatchError.of(0, null, "msg")
            );
        }

        @Test
        @DisplayName("should_ThrowException_When_MessageIsNull")
        void should_ThrowException_When_MessageIsNull() {
            assertThrows(NullPointerException.class, () ->
                    BatchResult.BatchError.of(0, "CODE", null)
            );
        }
    }
}

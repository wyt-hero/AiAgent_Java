package com.aiagent.common.dto;

import com.aiagent.common.exception.AgentException;
import com.aiagent.common.exception.CommonErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Result DTO Tests")
class ResultTest {

    @Nested
    @DisplayName("Success responses")
    class SuccessTests {

        @Test
        @DisplayName("should_ReturnSuccessWithData_When_OkWithData")
        void should_ReturnSuccessWithData_When_OkWithData() {
            Result<String> result = Result.ok("test-data");

            assertTrue(result.success());
            assertEquals(0, result.code());
            assertEquals("success", result.message());
            assertEquals("test-data", result.data());
            assertNotNull(result.timestamp());
        }

        @Test
        @DisplayName("should_ReturnSuccessWithoutData_When_Ok")
        void should_ReturnSuccessWithoutData_When_Ok() {
            Result<Object> result = Result.ok();

            assertTrue(result.success());
            assertNull(result.data());
        }

        @Test
        @DisplayName("should_ReturnSuccessWithCustomMessage_When_OkWithMessage")
        void should_ReturnSuccessWithCustomMessage_When_OkWithMessage() {
            Result<Integer> result = Result.ok(42, "operation completed");

            assertTrue(result.success());
            assertEquals("operation completed", result.message());
            assertEquals(42, result.data());
        }
    }

    @Nested
    @DisplayName("Error responses")
    class ErrorTests {

        @Test
        @DisplayName("should_ReturnError_When_Fail")
        void should_ReturnError_When_Fail() {
            Result<Object> result = Result.fail(10001, "Internal error");

            assertFalse(result.success());
            assertEquals(10001, result.code());
            assertEquals("Internal error", result.message());
            assertNull(result.data());
        }

        @Test
        @DisplayName("should_ReturnErrorWithMetadata_When_FailWithMetadata")
        void should_ReturnErrorWithMetadata_When_FailWithMetadata() {
            Map<String, Object> metadata = Map.of("traceId", "abc-123");
            Result<Object> result = Result.fail(10001, "Error", metadata);

            assertFalse(result.success());
            assertNotNull(result.metadata());
            assertEquals("abc-123", result.metadata().get("traceId"));
        }

        @Test
        @DisplayName("should_ReturnErrorFromErrorCode_When_FailWithErrorCode")
        void should_ReturnErrorFromErrorCode_When_FailWithErrorCode() {
            Result<Object> result = Result.fail(CommonErrorCode.UNAUTHORIZED);

            assertFalse(result.success());
            assertEquals(20001, result.code());
            assertEquals("Authentication required", result.message());
            assertNull(result.data());
        }

        @Test
        @DisplayName("should_ReturnErrorWithCustomMessage_When_FailWithErrorCodeAndMessage")
        void should_ReturnErrorWithCustomMessage_When_FailWithErrorCodeAndMessage() {
            Result<Object> result = Result.fail(CommonErrorCode.INVALID_ARGUMENT, "name is blank");

            assertFalse(result.success());
            assertEquals(10002, result.code());
            assertEquals("name is blank", result.message());
        }

        @Test
        @DisplayName("should_ReturnErrorFromException_When_FailWithBaseException")
        void should_ReturnErrorFromException_When_FailWithBaseException() {
            AgentException ex = AgentException.notFound("agent-001");
            Result<Object> result = Result.fail(ex);

            assertFalse(result.success());
            assertEquals(30002, result.code());
            assertTrue(result.message().contains("agent-001"));
        }

        @Test
        @DisplayName("should_ReturnSuccessWithMetadata_When_OkWithDataAndMetadata")
        void should_ReturnSuccessWithMetadata_When_OkWithDataAndMetadata() {
            Map<String, Object> metadata = Map.of("count", 5);
            Result<String> result = Result.ok("data", metadata);

            assertTrue(result.success());
            assertEquals("data", result.data());
            assertEquals(5, result.metadata().get("count"));
        }
    }
}

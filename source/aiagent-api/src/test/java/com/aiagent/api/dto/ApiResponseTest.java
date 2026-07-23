package com.aiagent.api.dto;

import com.aiagent.common.dto.Result;
import com.aiagent.common.exception.AgentException;
import com.aiagent.common.exception.CommonErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ApiResponse Tests")
class ApiResponseTest {

    @Nested
    @DisplayName("Success responses")
    class SuccessTests {

        @Test
        @DisplayName("should_ReturnOkWithData_When_OkWithData")
        void should_ReturnOkWithData_When_OkWithData() {
            ApiResponse<String> response = ApiResponse.ok("data");

            assertTrue(response.success());
            assertEquals(0, response.code());
            assertEquals("success", response.message());
            assertEquals("data", response.data());
            assertNotNull(response.timestamp());
        }

        @Test
        @DisplayName("should_ReturnOkWithoutData_When_Ok")
        void should_ReturnOkWithoutData_When_Ok() {
            ApiResponse<Object> response = ApiResponse.ok();

            assertTrue(response.success());
            assertNull(response.data());
        }

        @Test
        @DisplayName("should_ReturnOkWithMessage_When_OkWithMessage")
        void should_ReturnOkWithMessage_When_OkWithMessage() {
            ApiResponse<Integer> response = ApiResponse.ok(42, "done");

            assertTrue(response.success());
            assertEquals("done", response.message());
        }
    }

    @Nested
    @DisplayName("Error responses")
    class ErrorTests {

        @Test
        @DisplayName("should_ReturnError_When_ErrorWithCodeAndMessage")
        void should_ReturnError_When_ErrorWithCodeAndMessage() {
            ApiResponse<Object> response = ApiResponse.error(10001, "fail");

            assertFalse(response.success());
            assertEquals(10001, response.code());
            assertEquals("fail", response.message());
            assertNull(response.data());
        }

        @Test
        @DisplayName("should_ReturnErrorFromErrorCode_When_ErrorWithErrorCode")
        void should_ReturnErrorFromErrorCode_When_ErrorWithErrorCode() {
            ApiResponse<Object> response = ApiResponse.error(CommonErrorCode.UNAUTHORIZED);

            assertFalse(response.success());
            assertEquals(20001, response.code());
            assertEquals("Authentication required", response.message());
        }

        @Test
        @DisplayName("should_ReturnErrorFromException_When_ErrorWithBaseException")
        void should_ReturnErrorFromException_When_ErrorWith_BaseException() {
            AgentException ex = AgentException.notFound("agent-001");
            ApiResponse<Object> response = ApiResponse.error(ex);

            assertFalse(response.success());
            assertEquals(30002, response.code());
            assertTrue(response.message().contains("agent-001"));
        }
    }

    @Nested
    @DisplayName("Conversion")
    class ConversionTests {

        @Test
        @DisplayName("should_ConvertFromResult_When_FromResult")
        void should_ConvertFromResult_When_FromResult() {
            Result<String> result = Result.ok("hello");

            ApiResponse<String> response = ApiResponse.from(result);

            assertTrue(response.success());
            assertEquals("hello", response.data());
            assertEquals(result.timestamp(), response.timestamp());
        }

        @Test
        @DisplayName("should_ConvertFailedResult_When_FromFailedResult")
        void should_ConvertFailedResult_When_FromFailedResult() {
            Result<Object> result = Result.fail(10001, "error");

            ApiResponse<Object> response = ApiResponse.from(result);

            assertFalse(response.success());
            assertEquals(10001, response.code());
        }
    }

    @Test
    @DisplayName("should_ThrowException_When_MessageIsNull")
    void should_ThrowException_When_MessageIsNull() {
        assertThrows(NullPointerException.class, () ->
                new ApiResponse<>(true, 0, null, null, null)
        );
    }
}

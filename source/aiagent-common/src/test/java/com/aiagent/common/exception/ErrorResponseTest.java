package com.aiagent.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ErrorResponse Tests")
class ErrorResponseTest {

    @Test
    @DisplayName("should_CreateErrorResponse_When_FromBaseException")
    void should_CreateErrorResponse_When_FromBaseException() {
        AgentException ex = AgentException.notFound("agent-001");

        ErrorResponse response = ErrorResponse.from(ex);

        assertEquals(CommonErrorCode.AGENT_NOT_FOUND.code(), response.code());
        assertEquals("AGENT_NOT_FOUND", response.error());
        assertTrue(response.message().contains("agent-001"));
        assertNull(response.path());
        assertNull(response.traceId());
        assertNotNull(response.timestamp());
    }

    @Test
    @DisplayName("should_CreateErrorResponse_When_WithPathAndTraceId")
    void should_CreateErrorResponse_When_WithPathAndTraceId() {
        AgentException ex = AgentException.notFound("agent-001");

        ErrorResponse response = ErrorResponse.from(ex, "/api/v1/agents", "trace-123");

        assertEquals("/api/v1/agents", response.path());
        assertEquals("trace-123", response.traceId());
    }

    @Test
    @DisplayName("should_IncludeContext_When_ExceptionHasContext")
    void should_IncludeContext_When_ExceptionHasContext() {
        Map<String, Object> context = Map.of("agentId", "a1", "step", 3);
        AgentException ex = new AgentException(
                CommonErrorCode.AGENT_EXECUTION_FAILED, "failed", null, context
        );

        ErrorResponse response = ErrorResponse.from(ex);

        assertNotNull(response.details());
        assertEquals(2, response.details().size());
        assertEquals("a1", response.details().get("agentId"));
    }

    @Test
    @DisplayName("should_CreateErrorResponse_When_FromErrorCode")
    void should_CreateErrorResponse_When_FromErrorCode() {
        ErrorResponse response = ErrorResponse.of(CommonErrorCode.UNAUTHORIZED);

        assertEquals(20001, response.code());
        assertEquals("UNAUTHORIZED", response.error());
        assertEquals("Authentication required", response.message());
        assertNull(response.path());
    }

    @Test
    @DisplayName("should_CreateErrorResponse_When_WithCustomMessageAndPath")
    void should_CreateErrorResponse_When_WithCustomMessageAndPath() {
        ErrorResponse response = ErrorResponse.of(
                CommonErrorCode.INVALID_ARGUMENT, "name is required", "/api/v1/agents"
        );

        assertEquals(10002, response.code());
        assertEquals("name is required", response.message());
        assertEquals("/api/v1/agents", response.path());
    }

    @Test
    @DisplayName("should_CreateErrorResponse_When_WithAllFields")
    void should_CreateErrorResponse_When_WithAllFields() {
        Map<String, Object> details = Map.of("retryAfter", 5);

        ErrorResponse response = ErrorResponse.of(
                50001, "PROMPT_RENDER_FAILED", "render error", "/api/prompt",
                "trace-456", details
        );

        assertEquals(50001, response.code());
        assertEquals("PROMPT_RENDER_FAILED", response.error());
        assertEquals("render error", response.message());
        assertEquals("/api/prompt", response.path());
        assertEquals("trace-456", response.traceId());
        assertEquals(5, response.details().get("retryAfter"));
    }

    @Test
    @DisplayName("should_ThrowNPE_When_ErrorIsNull")
    void should_ThrowNPE_When_ErrorIsNull() {
        assertThrows(NullPointerException.class, () ->
                new ErrorResponse(100, null, "msg", null, null, null, null)
        );
    }

    @Test
    @DisplayName("should_ThrowNPE_When_MessageIsNull")
    void should_ThrowNPE_When_MessageIsNull() {
        assertThrows(NullPointerException.class, () ->
                new ErrorResponse(100, "ERR", null, null, null, null, null)
        );
    }

    @Test
    @DisplayName("should_SetDefaultTimestamp_When_TimestampIsNull")
    void should_SetDefaultTimestamp_When_TimestampIsNull() {
        ErrorResponse response = new ErrorResponse(100, "ERR", "msg", null, null, null, null);
        assertNotNull(response.timestamp());
    }

    @Test
    @DisplayName("should_CopyDetails_When_DetailsProvided")
    void should_CopyDetails_When_DetailsProvided() {
        Map<String, Object> details = Map.of("key", "value");
        ErrorResponse response = new ErrorResponse(100, "ERR", "msg", null, null, null, details);

        assertEquals("value", response.details().get("key"));
        assertThrows(UnsupportedOperationException.class, () ->
                response.details().put("new", "val")
        );
    }
}

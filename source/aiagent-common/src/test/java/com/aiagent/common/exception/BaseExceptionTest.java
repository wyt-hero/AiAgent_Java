package com.aiagent.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exception Hierarchy Tests")
class BaseExceptionTest {

    @Test
    @DisplayName("should_CreateAgentException_When_NotFound")
    void should_CreateAgentException_When_NotFound() {
        AgentException ex = AgentException.notFound("agent-001");

        assertEquals(CommonErrorCode.AGENT_NOT_FOUND, ex.getErrorCode());
        assertEquals(404, ex.httpStatus());
        assertTrue(ex.getMessage().contains("agent-001"));
    }

    @Test
    @DisplayName("should_CreateToolException_When_NotFound")
    void should_CreateToolException_When_NotFound() {
        ToolException ex = ToolException.notFound("search_db");

        assertEquals(CommonErrorCode.TOOL_NOT_FOUND, ex.getErrorCode());
        assertEquals(404, ex.httpStatus());
        assertTrue(ex.getMessage().contains("search_db"));
    }

    @Test
    @DisplayName("should_CreateExceptionWithContext_When_ContextProvided")
    void should_CreateExceptionWithContext_When_ContextProvided() {
        Map<String, Object> context = Map.of("agentId", "a1", "step", 3);
        AgentException ex = new AgentException(
                CommonErrorCode.AGENT_EXECUTION_FAILED,
                "Agent failed",
                null,
                context
        );

        assertEquals(2, ex.getContext().size());
        assertTrue(ex.getMessage().contains("context="));
    }

    @Test
    @DisplayName("should_ReturnCorrectHttpStatus_When_DifferentErrorCodes")
    void should_ReturnCorrectHttpStatus_When_DifferentErrorCodes() {
        assertEquals(401, CommonErrorCode.UNAUTHORIZED.httpStatus());
        assertEquals(403, CommonErrorCode.FORBIDDEN.httpStatus());
        assertEquals(404, CommonErrorCode.NOT_FOUND.httpStatus());
        assertEquals(409, CommonErrorCode.ALREADY_EXISTS.httpStatus());
        assertEquals(429, CommonErrorCode.RATE_LIMIT_EXCEEDED.httpStatus());
        assertEquals(500, CommonErrorCode.INTERNAL_ERROR.httpStatus());
    }

    @Test
    @DisplayName("should_CreateWorkflowException_When_CycleDetected")
    void should_CreateWorkflowException_When_CycleDetected() {
        WorkflowException ex = WorkflowException.cycleDetected();

        assertEquals(CommonErrorCode.WORKFLOW_CYCLE_DETECTED, ex.getErrorCode());
        assertEquals(500, ex.httpStatus());
    }

    @Test
    @DisplayName("should_CreateMcpException_When_ConnectionFailed")
    void should_CreateMcpException_When_ConnectionFailed() {
        McpException ex = McpException.connectionFailed("localhost:9999", new RuntimeException("refused"));

        assertEquals(CommonErrorCode.MCP_CONNECTION_FAILED, ex.getErrorCode());
        assertTrue(ex.getMessage().contains("localhost:9999"));
        assertNotNull(ex.getCause());
    }
}

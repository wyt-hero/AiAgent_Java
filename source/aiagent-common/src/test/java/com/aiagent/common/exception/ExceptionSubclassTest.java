package com.aiagent.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exception Subclass Factory Method Tests")
class ExceptionSubclassTest {

    @Nested
    @DisplayName("AgentException")
    class AgentExceptionTests {

        @Test
        @DisplayName("should_CreateExecutionFailed_When_WithDetailAndCause")
        void should_CreateExecutionFailed_When_WithDetailAndCause() {
            RuntimeException cause = new RuntimeException("timeout");
            AgentException ex = AgentException.executionFailed("step-3", cause);

            assertEquals(CommonErrorCode.AGENT_EXECUTION_FAILED, ex.getErrorCode());
            assertTrue(ex.getMessage().contains("step-3"));
            assertEquals(cause, ex.getCause());
        }

        @Test
        @DisplayName("should_CreateInvalidState_When_StateProvided")
        void should_CreateInvalidState_When_StateProvided() {
            AgentException ex = AgentException.invalidState("COMPLETED");

            assertEquals(CommonErrorCode.AGENT_STATE_INVALID, ex.getErrorCode());
            assertTrue(ex.getMessage().contains("COMPLETED"));
        }

        @Test
        @DisplayName("should_CreateWithContext_When_ContextMapProvided")
        void should_CreateWithContext_When_ContextMapProvided() {
            Map<String, Object> ctx = Map.of("step", 5, "agentId", "a1");
            AgentException ex = new AgentException(
                    CommonErrorCode.AGENT_EXECUTION_FAILED, "failed", null, ctx
            );

            assertEquals(2, ex.getContext().size());
            assertTrue(ex.getMessage().contains("context="));
        }
    }

    @Nested
    @DisplayName("ToolException")
    class ToolExceptionTests {

        @Test
        @DisplayName("should_CreateExecutionFailed_When_WithCause")
        void should_CreateExecutionFailed_When_WithCause() {
            ToolException ex = ToolException.executionFailed("search", new RuntimeException("err"));

            assertEquals(CommonErrorCode.TOOL_EXECUTION_FAILED, ex.getErrorCode());
            assertTrue(ex.getMessage().contains("search"));
            assertNotNull(ex.getCause());
        }

        @Test
        @DisplayName("should_CreateInvalidParam_When_ParamNameProvided")
        void should_CreateInvalidParam_When_ParamNameProvided() {
            ToolException ex = ToolException.invalidParam("query");

            assertEquals(CommonErrorCode.TOOL_PARAM_INVALID, ex.getErrorCode());
            assertTrue(ex.getMessage().contains("query"));
        }

        @Test
        @DisplayName("should_CreatePermissionDenied_When_ToolNameProvided")
        void should_CreatePermissionDenied_When_ToolNameProvided() {
            ToolException ex = ToolException.permissionDenied("admin_tool");

            assertEquals(CommonErrorCode.TOOL_PERMISSION_DENIED, ex.getErrorCode());
            assertEquals(403, ex.httpStatus());
        }
    }

    @Nested
    @DisplayName("PromptException")
    class PromptExceptionTests {

        @Test
        @DisplayName("should_CreateRenderFailed_When_WithDetailAndCause")
        void should_CreateRenderFailed_When_WithDetailAndCause() {
            PromptException ex = PromptException.renderFailed("template error", new RuntimeException());

            assertEquals(CommonErrorCode.PROMPT_RENDER_FAILED, ex.getErrorCode());
            assertTrue(ex.getMessage().contains("template error"));
        }

        @Test
        @DisplayName("should_CreateTokenExceeded_When_LimitsProvided")
        void should_CreateTokenExceeded_When_LimitsProvided() {
            PromptException ex = PromptException.tokenExceeded(4096, 8192);

            assertEquals(CommonErrorCode.PROMPT_TOKEN_EXCEEDED, ex.getErrorCode());
            assertTrue(ex.getMessage().contains("4096"));
            assertTrue(ex.getMessage().contains("8192"));
        }
    }

    @Nested
    @DisplayName("MemoryException")
    class MemoryExceptionTests {

        @Test
        @DisplayName("should_CreateReadFailed_When_WithDetailAndCause")
        void should_CreateReadFailed_When_WithDetailAndCause() {
            MemoryException ex = MemoryException.readFailed("Redis timeout", new RuntimeException());

            assertEquals(CommonErrorCode.MEMORY_READ_FAILED, ex.getErrorCode());
            assertTrue(ex.getMessage().contains("Redis timeout"));
        }

        @Test
        @DisplayName("should_CreateWriteFailed_When_WithDetailAndCause")
        void should_CreateWriteFailed_When_WithDetailAndCause() {
            MemoryException ex = MemoryException.writeFailed("disk full", new RuntimeException());

            assertEquals(CommonErrorCode.MEMORY_WRITE_FAILED, ex.getErrorCode());
        }

        @Test
        @DisplayName("should_CreateConsolidationFailed_When_WithDetailAndCause")
        void should_CreateConsolidationFailed_When_WithDetailAndCause() {
            MemoryException ex = MemoryException.consolidationFailed("merge conflict", new RuntimeException());

            assertEquals(CommonErrorCode.MEMORY_CONSOLIDATION_FAILED, ex.getErrorCode());
            assertTrue(ex.getMessage().contains("merge conflict"));
        }
    }

    @Nested
    @DisplayName("WorkflowException")
    class WorkflowExceptionTests {

        @Test
        @DisplayName("should_CreateExecutionFailed_When_WithDetailAndCause")
        void should_CreateExecutionFailed_When_WithDetailAndCause() {
            WorkflowException ex = WorkflowException.executionFailed("graph error", new RuntimeException());

            assertEquals(CommonErrorCode.WORKFLOW_EXECUTION_FAILED, ex.getErrorCode());
        }

        @Test
        @DisplayName("should_CreateNodeFailed_When_NodeIdAndCauseProvided")
        void should_CreateNodeFailed_When_NodeIdAndCauseProvided() {
            WorkflowException ex = WorkflowException.nodeFailed("node-5", new RuntimeException());

            assertEquals(CommonErrorCode.WORKFLOW_NODE_FAILED, ex.getErrorCode());
            assertTrue(ex.getMessage().contains("node-5"));
        }

        @Test
        @DisplayName("should_CreateCycleDetected_When_Called")
        void should_CreateCycleDetected_When_Called() {
            WorkflowException ex = WorkflowException.cycleDetected();

            assertEquals(CommonErrorCode.WORKFLOW_CYCLE_DETECTED, ex.getErrorCode());
        }
    }

    @Nested
    @DisplayName("McpException")
    class McpExceptionTests {

        @Test
        @DisplayName("should_CreateConnectionFailed_When_UrlAndCauseProvided")
        void should_CreateConnectionFailed_When_UrlAndCauseProvided() {
            McpException ex = McpException.connectionFailed("ws://localhost:8080", new RuntimeException());

            assertEquals(CommonErrorCode.MCP_CONNECTION_FAILED, ex.getErrorCode());
            assertTrue(ex.getMessage().contains("localhost:8080"));
        }

        @Test
        @DisplayName("should_CreateProtocolError_When_WithDetailAndCause")
        void should_CreateProtocolError_When_WithDetailAndCause() {
            McpException ex = McpException.protocolError("invalid message", new RuntimeException());

            assertEquals(CommonErrorCode.MCP_PROTOCOL_ERROR, ex.getErrorCode());
        }
    }

    @Nested
    @DisplayName("KnowledgeException")
    class KnowledgeExceptionTests {

        @Test
        @DisplayName("should_CreateIndexFailed_When_WithDetailAndCause")
        void should_CreateIndexFailed_When_WithDetailAndCause() {
            KnowledgeException ex = KnowledgeException.indexFailed("embedding timeout", new RuntimeException());

            assertEquals(CommonErrorCode.KNOWLEDGE_INDEX_FAILED, ex.getErrorCode());
            assertTrue(ex.getMessage().contains("embedding timeout"));
        }

        @Test
        @DisplayName("should_CreateRetrievalFailed_When_WithDetailAndCause")
        void should_CreateRetrievalFailed_When_WithDetailAndCause() {
            KnowledgeException ex = KnowledgeException.retrievalFailed("vector store down", new RuntimeException());

            assertEquals(CommonErrorCode.KNOWLEDGE_RETRIEVAL_FAILED, ex.getErrorCode());
        }
    }

    @Nested
    @DisplayName("BaseException Common Behavior")
    class BaseExceptionCommonTests {

        @Test
        @DisplayName("should_ReturnEmptyContext_When_NoContextProvided")
        void should_ReturnEmptyContext_When_NoContextProvided() {
            AgentException ex = AgentException.timeout();

            assertTrue(ex.getContext().isEmpty());
        }

        @Test
        @DisplayName("should_ReturnMessageWithContext_When_ContextPresent")
        void should_ReturnMessageWithContext_When_ContextPresent() {
            AgentException ex = new AgentException(
                    CommonErrorCode.AGENT_EXECUTION_FAILED, "failed", null, Map.of("key", "val")
            );

            assertTrue(ex.getMessage().contains("context="));
            assertTrue(ex.getMessage().contains("key"));
        }

        @Test
        @DisplayName("should_ReturnMessageWithoutContext_When_ContextEmpty")
        void should_ReturnMessageWithoutContext_When_ContextEmpty() {
            AgentException ex = AgentException.notFound("a1");

            assertFalse(ex.getMessage().contains("context="));
        }

        @Test
        @DisplayName("should_ReturnHttpStatus_When_ErrorCodeDefinesIt")
        void should_ReturnHttpStatus_When_ErrorCodeDefinesIt() {
            assertEquals(401, new AgentException(CommonErrorCode.UNAUTHORIZED).httpStatus());
            assertEquals(403, new ToolException(CommonErrorCode.FORBIDDEN).httpStatus());
            assertEquals(404, AgentException.notFound("x").httpStatus());
            assertEquals(404, ToolException.notFound("x").httpStatus());
            assertEquals(409, new AgentException(CommonErrorCode.ALREADY_EXISTS).httpStatus());
            assertEquals(429, new AgentException(CommonErrorCode.RATE_LIMIT_EXCEEDED).httpStatus());
            assertEquals(500, AgentException.timeout().httpStatus());
        }
    }
}

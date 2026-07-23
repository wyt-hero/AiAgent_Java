package com.aiagent.api.exception;

import com.aiagent.common.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/test");
    }

    @Nested
    @DisplayName("Framework exception handling")
    class FrameworkExceptionTests {

        @Test
        @DisplayName("should_Return400_When_ValidationException")
        void should_Return400_When_ValidationException() {
            ValidationException ex = ValidationException.of("name", null, "must not be blank");

            ResponseEntity<ErrorResponse> response = handler.handleValidationException(ex, request);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(CommonErrorCode.INVALID_ARGUMENT.code(), response.getBody().code());
        }

        @Test
        @DisplayName("should_ReturnCorrectStatus_When_AgentException")
        void should_ReturnCorrectStatus_When_AgentException() {
            AgentException ex = AgentException.notFound("agent-001");

            ResponseEntity<ErrorResponse> response = handler.handleAgentException(ex, request);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(30002, response.getBody().code());
        }

        @Test
        @DisplayName("should_ReturnCorrectStatus_When_ToolException")
        void should_ReturnCorrectStatus_When_ToolException() {
            ToolException ex = ToolException.notFound("tool-001");

            ResponseEntity<ErrorResponse> response = handler.handleToolException(ex, request);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals(40001, response.getBody().code());
        }

        @Test
        @DisplayName("should_Return500_When_PromptException")
        void should_Return500_When_PromptException() {
            PromptException ex = PromptException.renderFailed("template error", new RuntimeException("cause"));

            ResponseEntity<ErrorResponse> response = handler.handlePromptException(ex, request);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }

        @Test
        @DisplayName("should_Return500_When_MemoryException")
        void should_Return500_When_MemoryException() {
            MemoryException ex = MemoryException.readFailed("memory error", new RuntimeException("cause"));

            ResponseEntity<ErrorResponse> response = handler.handleMemoryException(ex, request);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }

        @Test
        @DisplayName("should_Return500_When_WorkflowException")
        void should_Return500_When_WorkflowException() {
            WorkflowException ex = WorkflowException.executionFailed("failed", new RuntimeException("cause"));

            ResponseEntity<ErrorResponse> response = handler.handleWorkflowException(ex, request);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }

        @Test
        @DisplayName("should_Return500_When_McpException")
        void should_Return500_When_McpException() {
            McpException ex = McpException.connectionFailed("mcp error", new RuntimeException("cause"));

            ResponseEntity<ErrorResponse> response = handler.handleMcpException(ex, request);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }

        @Test
        @DisplayName("should_Return500_When_KnowledgeException")
        void should_Return500_When_KnowledgeException() {
            KnowledgeException ex = KnowledgeException.retrievalFailed("search error", new RuntimeException("cause"));

            ResponseEntity<ErrorResponse> response = handler.handleKnowledgeException(ex, request);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("Spring exception handling")
    class SpringExceptionTests {

        @Test
        @DisplayName("should_Return400_When_MissingServletRequestParameter")
        void should_Return400_When_MissingServletRequestParameter() {
            MissingServletRequestParameterException ex =
                    new MissingServletRequestParameterException("agentId", "GET");

            ResponseEntity<ErrorResponse> response = handler.handleMissingParam(ex, request);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertTrue(response.getBody().message().contains("agentId"));
        }

        @Test
        @DisplayName("should_Return400_When_MethodArgumentTypeMismatch")
        void should_Return400_When_MethodArgumentTypeMismatch() {
            MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
            when(ex.getName()).thenReturn("page");

            ResponseEntity<ErrorResponse> response = handler.handleTypeMismatch(ex, request);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertTrue(response.getBody().message().contains("page"));
        }

        @Test
        @DisplayName("should_Return500_When_GenericException")
        void should_Return500_When_GenericException() {
            Exception ex = new RuntimeException("unexpected");

            ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex, request);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals(CommonErrorCode.INTERNAL_ERROR.code(), response.getBody().code());
        }
    }

    @Test
    @DisplayName("should_IncludePath_When_HandlingException")
    void should_IncludePath_When_HandlingException() {
        AgentException ex = AgentException.notFound("agent-001");

        ResponseEntity<ErrorResponse> response = handler.handleAgentException(ex, request);

        assertEquals("/api/v1/test", response.getBody().path());
    }
}

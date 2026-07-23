package com.aiagent.common.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DefaultExceptionAnalyzer Tests")
class DefaultExceptionAnalyzerTest {

    private DefaultExceptionAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new DefaultExceptionAnalyzer();
    }

    @Test
    @DisplayName("should_AnalyzeAgentException_When_NotFound")
    void should_AnalyzeAgentException_When_NotFound() {
        AgentException ex = AgentException.notFound("agent-001");

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertEquals(ExceptionAnalyzer.Severity.INFO, result.severity());
        assertEquals("AGENT", result.category());
        assertFalse(result.retryable());
        assertFalse(result.suggestions().isEmpty());
    }

    @Test
    @DisplayName("should_AnalyzeTimeoutException_When_Retryable")
    void should_AnalyzeTimeoutException_When_Retryable() {
        AgentException ex = AgentException.timeout();

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertEquals(ExceptionAnalyzer.Severity.ERROR, result.severity());
        assertEquals("AGENT", result.category());
        assertTrue(result.retryable());
        assertTrue(result.suggestions().contains("Retry the operation"));
    }

    @Test
    @DisplayName("should_AnalyzeAuthException_When_Unauthorized")
    void should_AnalyzeAuthException_When_Unauthorized() {
        AgentException ex = new AgentException(CommonErrorCode.UNAUTHORIZED);

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertEquals(ExceptionAnalyzer.Severity.WARNING, result.severity());
        assertEquals("AUTH", result.category());
        assertFalse(result.retryable());
        assertTrue(result.suggestions().contains("Verify authentication credentials"));
    }

    @Test
    @DisplayName("should_AnalyzeToolException_When_ExecutionFailed")
    void should_AnalyzeToolException_When_ExecutionFailed() {
        ToolException ex = ToolException.executionFailed("search_db", new RuntimeException("timeout"));

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertEquals("TOOL", result.category());
        assertTrue(result.suggestions().stream().anyMatch(s -> s.contains("tool")));
    }

    @Test
    @DisplayName("should_AnalyzeMcpException_When_ConnectionFailed")
    void should_AnalyzeMcpException_When_ConnectionFailed() {
        McpException ex = McpException.connectionFailed("localhost:9999", new RuntimeException());

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertEquals("MCP", result.category());
        assertTrue(result.retryable());
    }

    @Test
    @DisplayName("should_AnalyzeWorkflowException_When_CycleDetected")
    void should_AnalyzeWorkflowException_When_CycleDetected() {
        WorkflowException ex = WorkflowException.cycleDetected();

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertEquals("WORKFLOW", result.category());
        assertFalse(result.retryable());
    }

    @Test
    @DisplayName("should_AnalyzeMemoryException_When_ReadFailed")
    void should_AnalyzeMemoryException_When_ReadFailed() {
        MemoryException ex = MemoryException.readFailed("Redis timeout", new RuntimeException());

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertEquals("MEMORY", result.category());
    }

    @Test
    @DisplayName("should_AnalyzeKnowledgeException_When_IndexFailed")
    void should_AnalyzeKnowledgeException_When_IndexFailed() {
        KnowledgeException ex = KnowledgeException.indexFailed("embedding error", new RuntimeException());

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertEquals("KNOWLEDGE", result.category());
    }

    @Test
    @DisplayName("should_AnalyzePromptException_When_TokenExceeded")
    void should_AnalyzePromptException_When_TokenExceeded() {
        PromptException ex = PromptException.tokenExceeded(4096, 5000);

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertEquals("PROMPT", result.category());
    }

    @Test
    @DisplayName("should_AnalyzeValidationError_When_IllegalArgumentException")
    void should_AnalyzeValidationError_When_IllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("bad input");

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertEquals(ExceptionAnalyzer.Severity.INFO, result.severity());
        assertEquals("VALIDATION", result.category());
        assertFalse(result.retryable());
    }

    @Test
    @DisplayName("should_AnalyzeUnknownException_When_RuntimeException")
    void should_AnalyzeUnknownException_When_RuntimeException() {
        RuntimeException ex = new RuntimeException("unexpected error");

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertEquals(ExceptionAnalyzer.Severity.ERROR, result.severity());
        assertEquals("UNKNOWN", result.category());
        assertEquals("UNKNOWN_ERROR", result.errorCode());
        assertFalse(result.retryable());
    }

    @Test
    @DisplayName("should_IncludeErrorCode_When_BaseException")
    void should_IncludeErrorCode_When_BaseException() {
        AgentException ex = AgentException.notFound("a1");

        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertTrue(result.errorCode().contains("30002"));
    }

    @Test
    @DisplayName("should_ReturnImmutableSuggestions_When_Accessed")
    void should_ReturnImmutableSuggestions_When_Accessed() {
        AgentException ex = AgentException.timeout();
        ExceptionAnalyzer.AnalysisResult result = analyzer.analyze(ex);

        assertThrows(UnsupportedOperationException.class, () ->
                result.suggestions().add("hack")
        );
    }
}

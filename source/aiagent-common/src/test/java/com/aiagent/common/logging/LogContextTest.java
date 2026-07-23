package com.aiagent.common.logging;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LogContext Tests")
class LogContextTest {

    @AfterEach
    void tearDown() {
        LogContext.clear();
    }

    @Test
    @DisplayName("should_InitializeContext_When_Start")
    void should_InitializeContext_When_Start() {
        String requestId = LogContext.start();

        assertNotNull(requestId);
        assertEquals(requestId, LogContext.getRequestId());
        assertNotNull(LogContext.getTraceId());
        assertEquals("AiAgent", MDC.get(LogContext.KEY_FRAMEWORK));
    }

    @Test
    @DisplayName("should_UseExistingRequestId_When_StartWithId")
    void should_UseExistingRequestId_When_StartWithId() {
        String requestId = LogContext.start("custom-request-id");

        assertEquals("custom-request-id", LogContext.getRequestId());
        assertNotNull(LogContext.getTraceId());
    }

    @Test
    @DisplayName("should_SetAgentId_When_Provided")
    void should_SetAgentId_When_Provided() {
        LogContext.start();
        LogContext.setAgentId("agent-001");

        assertEquals("agent-001", LogContext.getAgentId());
    }

    @Test
    @DisplayName("should_SetSessionId_When_Provided")
    void should_SetSessionId_When_Provided() {
        LogContext.start();
        LogContext.setSessionId("session-abc");

        assertEquals("session-abc", LogContext.getSessionId());
    }

    @Test
    @DisplayName("should_ClearAllContext_When_Clear")
    void should_ClearAllContext_When_Clear() {
        LogContext.start();
        LogContext.setAgentId("agent-001");
        LogContext.setSessionId("session-abc");

        LogContext.clear();

        assertNull(LogContext.getRequestId());
        assertNull(LogContext.getTraceId());
        assertNull(LogContext.getAgentId());
        assertNull(LogContext.getSessionId());
    }

    @Test
    @DisplayName("should_RemoveOnlyFrameworkKeys_When_Remove")
    void should_RemoveOnlyFrameworkKeys_When_Remove() {
        LogContext.start();
        MDC.put("customKey", "customValue");

        LogContext.remove();

        assertNull(LogContext.getRequestId());
        assertEquals("customValue", MDC.get("customKey"));
    }

    @Test
    @DisplayName("should_CaptureAndRestoreContext_When_SnapshotRestore")
    void should_CaptureAndRestoreContext_When_SnapshotRestore() {
        LogContext.start();
        LogContext.setAgentId("agent-001");

        Map<String, String> snapshot = LogContext.snapshot();
        LogContext.clear();

        assertNull(LogContext.getRequestId());

        LogContext.restore(snapshot);

        assertNotNull(LogContext.getRequestId());
        assertEquals("agent-001", LogContext.getAgentId());
    }

    @Test
    @DisplayName("should_ThrowException_When_NullRequestId")
    void should_ThrowException_When_NullRequestId() {
        assertThrows(NullPointerException.class, () -> LogContext.start(null));
    }

    @Test
    @DisplayName("should_ThrowException_When_NullAgentId")
    void should_ThrowException_When_NullAgentId() {
        assertThrows(NullPointerException.class, () -> LogContext.setAgentId(null));
    }
}

package com.aiagent.common.logging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LogMarkers Tests")
class LogMarkersTest {

    @Test
    @DisplayName("should_HaveAllRequiredMarkers")
    void should_HaveAllRequiredMarkers() {
        assertNotNull(LogMarkers.SECURITY);
        assertNotNull(LogMarkers.AUDIT);
        assertNotNull(LogMarkers.AGENT);
        assertNotNull(LogMarkers.TOOL);
        assertNotNull(LogMarkers.WORKFLOW);
        assertNotNull(LogMarkers.MEMORY);
        assertNotNull(LogMarkers.MCP);
        assertNotNull(LogMarkers.PERFORMANCE);
        assertNotNull(LogMarkers.FATAL);
    }

    @Test
    @DisplayName("should_HaveCorrectMarkerNames")
    void should_HaveCorrectMarkerNames() {
        assertEquals("SECURITY", LogMarkers.SECURITY.getName());
        assertEquals("AUDIT", LogMarkers.AUDIT.getName());
        assertEquals("AGENT", LogMarkers.AGENT.getName());
        assertEquals("TOOL", LogMarkers.TOOL.getName());
        assertEquals("WORKFLOW", LogMarkers.WORKFLOW.getName());
        assertEquals("MEMORY", LogMarkers.MEMORY.getName());
        assertEquals("MCP", LogMarkers.MCP.getName());
        assertEquals("PERFORMANCE", LogMarkers.PERFORMANCE.getName());
        assertEquals("FATAL", LogMarkers.FATAL.getName());
    }
}

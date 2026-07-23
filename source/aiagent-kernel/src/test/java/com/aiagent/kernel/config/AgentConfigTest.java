package com.aiagent.kernel.config;
import com.aiagent.kernel.context.AgentState;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
class AgentConfigTest {
    @Test
    void builder_shouldCreateConfigWithDefaults() {
        var config = AgentConfig.builder().build();
        assertEquals(10, config.maxIterations());
        assertEquals(Duration.ofMinutes(5), config.timeout());
        assertEquals(0, config.maxRetries());
        assertEquals(Duration.ofSeconds(1), config.retryDelay());
        assertEquals(AgentState.IDLE, config.initialState());
        assertTrue(config.recordSteps());
        assertTrue(config.emitEvents());
        assertTrue(config.properties().isEmpty());
    }
    @Test
    void builder_shouldCreateConfigWithCustomValues() {
        var config = AgentConfig.builder().maxIterations(20).timeout(Duration.ofMinutes(10)).maxRetries(3).retryDelay(Duration.ofSeconds(2)).initialState(AgentState.RUNNING).recordSteps(false).emitEvents(false).properties(Map.of("key", "value")).build();
        assertEquals(20, config.maxIterations());
        assertEquals(Duration.ofMinutes(10), config.timeout());
        assertEquals(3, config.maxRetries());
        assertEquals(AgentState.RUNNING, config.initialState());
        assertFalse(config.recordSteps());
        assertEquals("value", config.properties().get("key"));
    }
    @Test
    void builder_property_shouldAddSingleProperty() {
        var config = AgentConfig.builder().property("model", "gpt-4").property("temperature", "0.7").build();
        assertEquals(2, config.properties().size());
        assertEquals("gpt-4", config.properties().get("model"));
    }
    @Test
    void constructor_shouldRejectInvalidMaxIterations() {
        assertThrows(IllegalArgumentException.class, () -> AgentConfig.builder().maxIterations(0).build());
    }
    @Test
    void constructor_shouldRejectNegativeMaxRetries() {
        assertThrows(IllegalArgumentException.class, () -> AgentConfig.builder().maxRetries(-1).build());
    }
    @Test
    void constructor_shouldRejectNullTimeout() {
        assertThrows(NullPointerException.class, () -> new AgentConfig(10, null, 0, Duration.ofSeconds(1), AgentState.IDLE, true, true, Map.of()));
    }
    @Test
    void constructor_shouldHandleNullPropertiesAsEmptyMap() {
        var config = new AgentConfig(10, Duration.ofMinutes(5), 0, Duration.ofSeconds(1), AgentState.IDLE, true, true, null);
        assertNotNull(config.properties());
        assertTrue(config.properties().isEmpty());
    }
    @Test
    void constructor_shouldDefensiveCopyProperties() {
        var props = Map.of("key", "value");
        var config = AgentConfig.builder().properties(props).build();
        assertNotSame(props, config.properties());
        assertEquals(props, config.properties());
    }
}

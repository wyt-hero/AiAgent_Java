package com.aiagent.kernel.registry;

import com.aiagent.kernel.Agent;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AgentDescriptorTest {

    private final Agent testAgent = new Agent() {
        @Override public String id() { return "agent-1"; }
        @Override public String name() { return "Test Agent"; }
    };

    @Test
    void constructor_shouldCreateWithRequiredFields() {
        var desc = new AgentDescriptor("id-1", "Agent One", "A test agent", testAgent, true, Map.of());
        assertEquals("id-1", desc.agentId());
        assertEquals("Agent One", desc.name());
        assertEquals("A test agent", desc.description());
        assertTrue(desc.active());
        assertSame(testAgent, desc.agent());
        assertTrue(desc.metadata().isEmpty());
    }

    @Test
    void constructor_shouldDefaultDescriptionToEmpty() {
        var desc = new AgentDescriptor("id-1", "Agent", null, testAgent, true, null);
        assertEquals("", desc.description());
        assertTrue(desc.metadata().isEmpty());
    }

    @Test
    void constructor_shouldRejectNullAgentId() {
        assertThrows(NullPointerException.class,
                () -> new AgentDescriptor(null, "name", "desc", testAgent, true, Map.of()));
    }

    @Test
    void constructor_shouldRejectNullName() {
        assertThrows(NullPointerException.class,
                () -> new AgentDescriptor("id", null, "desc", testAgent, true, Map.of()));
    }

    @Test
    void constructor_shouldRejectNullAgent() {
        assertThrows(NullPointerException.class,
                () -> new AgentDescriptor("id", "name", "desc", null, true, Map.of()));
    }

    @Test
    void withActive_shouldReturnNewInstance() {
        var desc = new AgentDescriptor("id-1", "Agent", "", testAgent, true, Map.of());
        var inactive = desc.withActive(false);

        assertTrue(desc.active());
        assertFalse(inactive.active());
        assertEquals(desc.agentId(), inactive.agentId());
    }

    @Test
    void withMetadata_shouldAddEntry() {
        var desc = new AgentDescriptor("id-1", "Agent", "", testAgent, true, Map.of());
        var updated = desc.withMetadata("version", "1.0");

        assertTrue(desc.metadata().isEmpty());
        assertEquals("1.0", updated.metadata().get("version"));
    }

    @Test
    void metadata_shouldBeImmutable() {
        var desc = new AgentDescriptor("id-1", "Agent", "", testAgent, true, Map.of("k", "v"));
        assertThrows(UnsupportedOperationException.class, () -> desc.metadata().put("new", "val"));
    }
}

package com.aiagent.kernel.registry;

import com.aiagent.kernel.Agent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AgentRegistryTest {

    private AgentRegistry registry;

    private final Agent agent1 = new Agent() {
        @Override public String id() { return "agent-1"; }
        @Override public String name() { return "Agent One"; }
    };

    private final Agent agent2 = new Agent() {
        @Override public String id() { return "agent-2"; }
        @Override public String name() { return "Agent Two"; }
    };

    @BeforeEach
    void setUp() {
        registry = new AgentRegistry();
    }

    @Test
    void register_shouldAddAgent() {
        registry.register(agent1);
        assertEquals(1, registry.size());
        assertTrue(registry.contains("agent-1"));
    }

    @Test
    void register_shouldRejectNullAgent() {
        assertThrows(NullPointerException.class, () -> registry.register((Agent) null));
    }

    @Test
    void register_shouldRejectDuplicate() {
        registry.register(agent1);
        assertThrows(IllegalArgumentException.class, () -> registry.register(agent1));
    }

    @Test
    void registerDescriptor_shouldAddWithFullMetadata() {
        var desc = new AgentDescriptor("agent-x", "Agent X", "desc", agent1, true, Map.of("key", "val"));
        registry.register(desc);

        var found = registry.get("agent-x");
        assertEquals("Agent X", found.name());
        assertEquals("desc", found.description());
        assertEquals("val", found.metadata().get("key"));
    }

    @Test
    void registerDescriptor_shouldRejectNull() {
        assertThrows(NullPointerException.class, () -> registry.register((AgentDescriptor) null));
    }

    @Test
    void unregister_shouldRemoveAgent() {
        registry.register(agent1);
        assertTrue(registry.unregister("agent-1"));
        assertFalse(registry.contains("agent-1"));
        assertEquals(0, registry.size());
    }

    @Test
    void unregister_shouldReturnFalseIfNotFound() {
        assertFalse(registry.unregister("nonexistent"));
    }

    @Test
    void get_shouldReturnDescriptor() {
        registry.register(agent1);
        var desc = registry.get("agent-1");
        assertEquals("agent-1", desc.agentId());
        assertEquals("Agent One", desc.name());
    }

    @Test
    void get_shouldThrowIfNotFound() {
        assertThrows(NoSuchElementException.class, () -> registry.get("missing"));
    }

    @Test
    void contains_shouldReturnCorrectStatus() {
        assertFalse(registry.contains("agent-1"));
        registry.register(agent1);
        assertTrue(registry.contains("agent-1"));
    }

    @Test
    void all_shouldReturnAllDescriptors() {
        registry.register(agent1);
        registry.register(agent2);
        assertEquals(2, registry.all().size());
    }

    @Test
    void all_shouldBeUnmodifiable() {
        registry.register(agent1);
        var all = registry.all();
        assertThrows(UnsupportedOperationException.class, () -> {
            // Attempt to cast and modify - should fail
            var list = (java.util.List<AgentDescriptor>) all;
            list.clear();
        });
    }

    @Test
    void activeAgents_shouldReturnOnlyActive() {
        var activeDesc = new AgentDescriptor("a1", "Active", "", agent1, true, Map.of());
        var inactiveDesc = new AgentDescriptor("a2", "Inactive", "", agent2, false, Map.of());

        registry.register(activeDesc);
        registry.register(inactiveDesc);

        var active = registry.activeAgents();
        assertEquals(1, active.size());
        assertEquals("a1", active.get(0).agentId());
    }

    @Test
    void setActive_shouldUpdateStatus() {
        registry.register(agent1);
        assertTrue(registry.get("agent-1").active());

        registry.setActive("agent-1", false);
        assertFalse(registry.get("agent-1").active());
    }

    @Test
    void clear_shouldRemoveAll() {
        registry.register(agent1);
        registry.register(agent2);
        registry.clear();
        assertEquals(0, registry.size());
    }

    @Test
    void size_shouldReflectRegistrations() {
        assertEquals(0, registry.size());
        registry.register(agent1);
        assertEquals(1, registry.size());
        registry.register(agent2);
        assertEquals(2, registry.size());
        registry.unregister("agent-1");
        assertEquals(1, registry.size());
    }
}

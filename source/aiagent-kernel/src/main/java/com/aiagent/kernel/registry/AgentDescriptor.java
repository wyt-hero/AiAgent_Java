package com.aiagent.kernel.registry;

import com.aiagent.kernel.Agent;

import java.util.Map;
import java.util.Objects;

/**
 * Immutable descriptor for a registered agent.
 *
 * <p>Contains metadata about the agent including its factory,
 * configuration, and status.
 */
public record AgentDescriptor(
        /** Unique agent identifier. */
        String agentId,

        /** Human-readable agent name. */
        String name,

        /** Agent description. */
        String description,

        /** The agent instance. */
        Agent agent,

        /** Whether this agent is currently active. */
        boolean active,

        /** Registration metadata. */
        Map<String, String> metadata
) {
    public AgentDescriptor {
        Objects.requireNonNull(agentId, "agentId must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(agent, "agent must not be null");

        description = (description == null) ? "" : description;
        metadata = (metadata == null) ? Map.of() : Map.copyOf(metadata);
    }

    /**
     * Return a copy with updated active status.
     */
    public AgentDescriptor withActive(boolean newActive) {
        return new AgentDescriptor(agentId, name, description, agent, newActive, metadata);
    }

    /**
     * Return a copy with additional metadata.
     */
    public AgentDescriptor withMetadata(String key, String value) {
        var newMeta = new java.util.HashMap<>(metadata);
        newMeta.put(key, value);
        return new AgentDescriptor(agentId, name, description, agent, active, Map.copyOf(newMeta));
    }
}

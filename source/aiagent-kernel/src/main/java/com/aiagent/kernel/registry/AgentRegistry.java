package com.aiagent.kernel.registry;

import com.aiagent.kernel.Agent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe registry for managing agent instances.
 *
 * <p>Provides registration, lookup, and lifecycle management
 * for agents within the kernel.
 */
public class AgentRegistry {

    private final Map<String, AgentDescriptor> agents = new ConcurrentHashMap<>();

    /**
     * Register a new agent with default descriptor.
     *
     * @param agent the agent to register
     * @throws NullPointerException if agent is null
     * @throws IllegalArgumentException if agent id is already registered
     */
    public void register(Agent agent) {
        Objects.requireNonNull(agent, "agent must not be null");
        register(new AgentDescriptor(agent.id(), agent.name(), "", agent, true, Map.of()));
    }

    /**
     * Register an agent with a full descriptor.
     *
     * @param descriptor the agent descriptor
     * @throws NullPointerException     if descriptor is null
     * @throws IllegalArgumentException if agent id is already registered
     */
    public void register(AgentDescriptor descriptor) {
        Objects.requireNonNull(descriptor, "descriptor must not be null");
        AgentDescriptor existing = agents.putIfAbsent(descriptor.agentId(), descriptor);
        if (existing != null) {
            throw new IllegalArgumentException("Agent already registered: " + descriptor.agentId());
        }
    }

    /**
     * Unregister an agent by id.
     *
     * @param agentId the agent identifier
     * @return true if the agent was removed
     */
    public boolean unregister(String agentId) {
        return agents.remove(agentId) != null;
    }

    /**
     * Look up an agent by id.
     *
     * @param agentId the agent identifier
     * @return the AgentDescriptor
     * @throws NoSuchElementException if not found
     */
    public AgentDescriptor get(String agentId) {
        AgentDescriptor desc = agents.get(agentId);
        if (desc == null) {
            throw new NoSuchElementException("Agent not found: " + agentId);
        }
        return desc;
    }

    /**
     * Check if an agent is registered.
     *
     * @param agentId the agent identifier
     * @return true if registered
     */
    public boolean contains(String agentId) {
        return agents.containsKey(agentId);
    }

    /**
     * Return all registered agent descriptors.
     *
     * @return unmodifiable collection of descriptors
     */
    public Collection<AgentDescriptor> all() {
        return Collections.unmodifiableCollection(agents.values());
    }

    /**
     * Return all active agents.
     *
     * @return list of active descriptors
     */
    public List<AgentDescriptor> activeAgents() {
        return agents.values().stream()
                .filter(AgentDescriptor::active)
                .toList();
    }

    /**
     * Update an agent's active status.
     *
     * @param agentId the agent identifier
     * @param active  the new active status
     * @throws NoSuchElementException if not found
     */
    public void setActive(String agentId, boolean active) {
        agents.computeIfPresent(agentId, (id, desc) -> desc.withActive(active));
    }

    /**
     * Return the number of registered agents.
     */
    public int size() {
        return agents.size();
    }

    /**
     * Remove all registered agents.
     */
    public void clear() {
        agents.clear();
    }
}

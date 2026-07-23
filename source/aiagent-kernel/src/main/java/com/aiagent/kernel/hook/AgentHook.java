package com.aiagent.kernel.hook;

import com.aiagent.kernel.context.AgentContext;

/**
 * Hook interface for extending agent execution behavior.
 *
 * <p>Hooks are invoked at specific points during the execution loop.
 */
public interface AgentHook extends Comparable<AgentHook> {

    /**
     * Unique name of this hook.
     */
    String name();

    /**
     * Priority order (lower = earlier).
     */
    default int order() { return 100; }

    /**
     * Called before execution starts.
     */
    default void beforeExecute(AgentContext context) {}

    /**
     * Called after execution completes (success or failure).
     */
    default void afterExecute(AgentContext context) {}

    /**
     * Called before each think phase.
     */
    default void beforeThink(AgentContext context) {}

    /**
     * Called after each think phase.
     */
    default void afterThink(AgentContext context) {}

    /**
     * Called before each act phase.
     */
    default void beforeAct(AgentContext context) {}

    /**
     * Called after each act phase.
     */
    default void afterAct(AgentContext context) {}

    /**
     * Called before each observe phase.
     */
    default void beforeObserve(AgentContext context) {}

    /**
     * Called after each observe phase.
     */
    default void afterObserve(AgentContext context) {}

    @Override
    default int compareTo(AgentHook other) {
        return Integer.compare(this.order(), other.order());
    }
}

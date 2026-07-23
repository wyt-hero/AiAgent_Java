package com.aiagent.kernel.lifecycle;
import com.aiagent.kernel.context.AgentContext;
import com.aiagent.kernel.context.AgentState;
public interface AgentLifecycle {
    default AgentContext onInit(AgentContext context) { return context; }
    default AgentContext beforeIteration(AgentContext context) { return context; }
    default AgentContext afterIteration(AgentContext context) { return context; }
    default void onComplete(AgentContext context) {}
    default void onFailure(AgentContext context, Throwable cause) {}
    default void onCancel(AgentContext context) {}
    default boolean isValidTransition(AgentState from, AgentState to) {
        return switch (from) {
            case IDLE -> to == AgentState.RUNNING || to == AgentState.CANCELLED;
            case RUNNING -> to == AgentState.COMPLETED || to == AgentState.FAILED || to == AgentState.CANCELLED;
            case COMPLETED, FAILED, CANCELLED -> false;
        };
    }
}

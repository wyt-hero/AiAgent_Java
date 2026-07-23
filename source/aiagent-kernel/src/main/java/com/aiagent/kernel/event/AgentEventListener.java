package com.aiagent.kernel.event;
public interface AgentEventListener {
    void onEvent(AgentEvent event);
    default boolean supports(Class<? extends AgentEvent> eventType) { return true; }
}

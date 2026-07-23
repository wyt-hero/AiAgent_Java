package com.aiagent.kernel;
public interface Agent {
    String id();
    String name();
    default String description() { return ""; }
    default boolean isAvailable() { return true; }
}

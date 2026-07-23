package com.aiagent.kernel.hook;

import com.aiagent.kernel.context.AgentContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registry for managing agent hooks.
 *
 * <p>Hooks are sorted by order and invoked in sequence.
 */
public class HookRegistry {

    private final List<AgentHook> hooks = new ArrayList<>();

    public void register(AgentHook hook) {
        if (hook == null) throw new NullPointerException("hook must not be null");
        hooks.add(hook);
        Collections.sort(hooks);
    }

    public boolean unregister(String name) {
        return hooks.removeIf(h -> h.name().equals(name));
    }

    public void fireBeforeExecute(AgentContext ctx) {
        hooks.forEach(h -> h.beforeExecute(ctx));
    }

    public void fireAfterExecute(AgentContext ctx) {
        hooks.forEach(h -> h.afterExecute(ctx));
    }

    public void fireBeforeThink(AgentContext ctx) {
        hooks.forEach(h -> h.beforeThink(ctx));
    }

    public void fireAfterThink(AgentContext ctx) {
        hooks.forEach(h -> h.afterThink(ctx));
    }

    public void fireBeforeAct(AgentContext ctx) {
        hooks.forEach(h -> h.beforeAct(ctx));
    }

    public void fireAfterAct(AgentContext ctx) {
        hooks.forEach(h -> h.afterAct(ctx));
    }

    public void fireBeforeObserve(AgentContext ctx) {
        hooks.forEach(h -> h.beforeObserve(ctx));
    }

    public void fireAfterObserve(AgentContext ctx) {
        hooks.forEach(h -> h.afterObserve(ctx));
    }

    public int size() { return hooks.size(); }

    public List<AgentHook> hooks() { return List.copyOf(hooks); }
}

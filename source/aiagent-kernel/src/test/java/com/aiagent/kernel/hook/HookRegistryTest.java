package com.aiagent.kernel.hook;

import com.aiagent.kernel.context.AgentContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HookRegistryTest {

    private HookRegistry registry;
    private final AgentContext ctx = AgentContext.builder()
            .agentId("test-agent")
            .userMessage("hello")
            .build();

    @BeforeEach
    void setUp() {
        registry = new HookRegistry();
    }

    @Test
    void register_shouldAddHook() {
        AgentHook hook = namedHook("a", 100);
        registry.register(hook);
        assertEquals(1, registry.size());
    }

    @Test
    void register_shouldSortByOrder() {
        registry.register(namedHook("c", 300));
        registry.register(namedHook("a", 100));
        registry.register(namedHook("b", 200));

        List<AgentHook> hooks = registry.hooks();
        assertEquals("a", hooks.get(0).name());
        assertEquals("b", hooks.get(1).name());
        assertEquals("c", hooks.get(2).name());
    }

    @Test
    void register_shouldRejectNull() {
        assertThrows(NullPointerException.class, () -> registry.register(null));
    }

    @Test
    void unregister_shouldRemoveByName() {
        registry.register(namedHook("keep", 1));
        registry.register(namedHook("remove", 2));

        assertTrue(registry.unregister("remove"));
        assertEquals(1, registry.size());
        assertEquals("keep", registry.hooks().get(0).name());
    }

    @Test
    void unregister_shouldReturnFalseIfNotFound() {
        registry.register(namedHook("exists", 1));
        assertFalse(registry.unregister("nonexistent"));
    }

    @Test
    void fireBeforeExecute_shouldCallAllHooks() {
        List<String> callOrder = new ArrayList<>();
        registry.register(new AgentHook() {
            @Override public String name() { return "first"; }
            @Override public int order() { return 1; }
            @Override public void beforeExecute(AgentContext context) { callOrder.add("first"); }
        });
        registry.register(new AgentHook() {
            @Override public String name() { return "second"; }
            @Override public int order() { return 2; }
            @Override public void beforeExecute(AgentContext context) { callOrder.add("second"); }
        });

        registry.fireBeforeExecute(ctx);
        assertEquals(List.of("first", "second"), callOrder);
    }

    @Test
    void fireAfterExecute_shouldCallAllHooks() {
        List<String> callOrder = new ArrayList<>();
        registry.register(new AgentHook() {
            @Override public String name() { return "h1"; }
            @Override public int order() { return 1; }
            @Override public void afterExecute(AgentContext context) { callOrder.add("h1"); }
        });

        registry.fireAfterExecute(ctx);
        assertEquals(List.of("h1"), callOrder);
    }

    @Test
    void fireThink_shouldCallBeforeAndAfter() {
        List<String> calls = new ArrayList<>();
        registry.register(new AgentHook() {
            @Override public String name() { return "thinker"; }
            @Override public void beforeThink(AgentContext context) { calls.add("before"); }
            @Override public void afterThink(AgentContext context) { calls.add("after"); }
        });

        registry.fireBeforeThink(ctx);
        registry.fireAfterThink(ctx);
        assertEquals(List.of("before", "after"), calls);
    }

    @Test
    void fireAct_shouldCallBeforeAndAfter() {
        List<String> calls = new ArrayList<>();
        registry.register(new AgentHook() {
            @Override public String name() { return "actor"; }
            @Override public void beforeAct(AgentContext context) { calls.add("before"); }
            @Override public void afterAct(AgentContext context) { calls.add("after"); }
        });

        registry.fireBeforeAct(ctx);
        registry.fireAfterAct(ctx);
        assertEquals(List.of("before", "after"), calls);
    }

    @Test
    void fireObserve_shouldCallBeforeAndAfter() {
        List<String> calls = new ArrayList<>();
        registry.register(new AgentHook() {
            @Override public String name() { return "observer"; }
            @Override public void beforeObserve(AgentContext context) { calls.add("before"); }
            @Override public void afterObserve(AgentContext context) { calls.add("after"); }
        });

        registry.fireBeforeObserve(ctx);
        registry.fireAfterObserve(ctx);
        assertEquals(List.of("before", "after"), calls);
    }

    @Test
    void hooks_shouldReturnImmutableCopy() {
        registry.register(namedHook("x", 1));
        List<AgentHook> hooks = registry.hooks();
        assertThrows(UnsupportedOperationException.class, () -> hooks.add(namedHook("y", 2)));
    }

    @Test
    void emptyRegistry_shouldNotFailOnFire() {
        assertDoesNotThrow(() -> registry.fireBeforeExecute(ctx));
        assertDoesNotThrow(() -> registry.fireAfterExecute(ctx));
        assertDoesNotThrow(() -> registry.fireBeforeThink(ctx));
        assertDoesNotThrow(() -> registry.fireAfterThink(ctx));
    }

    private AgentHook namedHook(String name, int order) {
        return new AgentHook() {
            @Override public String name() { return name; }
            @Override public int order() { return order; }
        };
    }
}

package com.aiagent.kernel.hook;

import com.aiagent.kernel.context.AgentContext;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class AgentHookTest {

    private final AgentContext ctx = AgentContext.builder()
            .agentId("test-agent")
            .userMessage("hello")
            .build();

    @Test
    void defaultMethods_shouldNotThrow() {
        AgentHook hook = new AgentHook() {
            @Override public String name() { return "test"; }
        };

        assertDoesNotThrow(() -> hook.beforeExecute(ctx));
        assertDoesNotThrow(() -> hook.afterExecute(ctx));
        assertDoesNotThrow(() -> hook.beforeThink(ctx));
        assertDoesNotThrow(() -> hook.afterThink(ctx));
        assertDoesNotThrow(() -> hook.beforeAct(ctx));
        assertDoesNotThrow(() -> hook.afterAct(ctx));
        assertDoesNotThrow(() -> hook.beforeObserve(ctx));
        assertDoesNotThrow(() -> hook.afterObserve(ctx));
    }

    @Test
    void defaultOrder_shouldBe100() {
        AgentHook hook = new AgentHook() {
            @Override public String name() { return "default-order"; }
        };
        assertEquals(100, hook.order());
    }

    @Test
    void customOrder_shouldBeRespected() {
        AgentHook hook = new AgentHook() {
            @Override public String name() { return "custom"; }
            @Override public int order() { return 10; }
        };
        assertEquals(10, hook.order());
    }

    @Test
    void compareTo_shouldSortByOrder() {
        AgentHook low = new AgentHook() {
            @Override public String name() { return "low"; }
            @Override public int order() { return 1; }
        };
        AgentHook high = new AgentHook() {
            @Override public String name() { return "high"; }
            @Override public int order() { return 200; }
        };

        assertTrue(low.compareTo(high) < 0);
        assertTrue(high.compareTo(low) > 0);
        assertEquals(0, low.compareTo(low));
    }

    @Test
    void beforeExecute_shouldBeInvoked() {
        AtomicBoolean called = new AtomicBoolean(false);
        AgentHook hook = new AgentHook() {
            @Override public String name() { return "tracker"; }
            @Override public void beforeExecute(AgentContext context) { called.set(true); }
        };

        hook.beforeExecute(ctx);
        assertTrue(called.get());
    }

    @Test
    void afterThink_shouldReceiveContext() {
        AgentHook hook = new AgentHook() {
            @Override public String name() { return "ctx-check"; }
            @Override public void afterThink(AgentContext context) {
                assertEquals("test-agent", context.agentId());
            }
        };

        assertDoesNotThrow(() -> hook.afterThink(ctx));
    }
}

package com.aiagent.kernel.event;
import com.aiagent.kernel.context.AgentContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class EventBusTest {
    private EventBus eventBus;
    private final AgentContext ctx = AgentContext.builder().agentId("test-agent").userMessage("hello").build();
    @BeforeEach
    void setUp() { eventBus = new EventBus(); }
    @Test
    void register_shouldAddListener() {
        eventBus.register(event -> {});
        assertEquals(1, eventBus.listenerCount());
    }
    @Test
    void register_shouldRejectNull() {
        assertThrows(NullPointerException.class, () -> eventBus.register(null));
    }
    @Test
    void unregister_shouldRemoveListener() {
        AgentEventListener listener = event -> {};
        eventBus.register(listener);
        assertTrue(eventBus.unregister(listener));
        assertEquals(0, eventBus.listenerCount());
    }
    @Test
    void unregister_shouldReturnFalseForUnknown() {
        assertFalse(eventBus.unregister(event -> {}));
    }
    @Test
    void publish_shouldDeliverEventToListener() {
        List<AgentEvent> received = new ArrayList<>();
        eventBus.register(received::add);
        eventBus.publish(new AgentEvent.AgentStarted(ctx));
        assertEquals(1, received.size());
        assertInstanceOf(AgentEvent.AgentStarted.class, received.getFirst());
    }
    @Test
    void publish_shouldDeliverToMultipleListeners() {
        List<AgentEvent> r1 = new ArrayList<>(), r2 = new ArrayList<>();
        eventBus.register(r1::add);
        eventBus.register(r2::add);
        eventBus.publish(new AgentEvent.AgentCompleted(ctx, "done"));
        assertEquals(1, r1.size());
        assertEquals(1, r2.size());
    }
    @Test
    void publish_shouldSkipUnsupportedEventType() {
        List<AgentEvent> received = new ArrayList<>();
        eventBus.register(new AgentEventListener() {
            @Override public void onEvent(AgentEvent event) { received.add(event); }
            @Override public boolean supports(Class<? extends AgentEvent> t) { return t == AgentEvent.AgentStarted.class; }
        });
        eventBus.publish(new AgentEvent.AgentCompleted(ctx, "done"));
        assertTrue(received.isEmpty());
        eventBus.publish(new AgentEvent.AgentStarted(ctx));
        assertEquals(1, received.size());
    }
    @Test
    void publish_shouldCatchListenerExceptions() {
        eventBus.register(event -> { throw new RuntimeException("boom"); });
        List<AgentEvent> received = new ArrayList<>();
        eventBus.register(received::add);
        assertDoesNotThrow(() -> eventBus.publish(new AgentEvent.AgentStarted(ctx)));
        assertEquals(1, received.size());
    }
    @Test
    void publish_shouldRejectNull() {
        assertThrows(NullPointerException.class, () -> eventBus.publish(null));
    }
    @Test
    void clear_shouldRemoveAllListeners() {
        eventBus.register(event -> {});
        eventBus.register(event -> {});
        eventBus.clear();
        assertEquals(0, eventBus.listenerCount());
    }
    @Test
    void agentEvent_sealedTypes_shouldBeExhaustive() {
        assertNotNull(new AgentEvent.AgentStarted(ctx).timestamp());
        assertEquals("out", new AgentEvent.AgentCompleted(ctx, "out").output());
        assertNotNull(new AgentEvent.AgentFailed(ctx, new RuntimeException("err")).cause());
        assertNotNull(new AgentEvent.AgentCancelled(ctx).timestamp());
        assertEquals(1, new AgentEvent.IterationStarted(ctx, 1).iteration());
        assertEquals(2, new AgentEvent.IterationCompleted(ctx, 2).iteration());
        assertEquals("THINK", new AgentEvent.StepExecuted(ctx, "THINK", "x").phase());
    }
}

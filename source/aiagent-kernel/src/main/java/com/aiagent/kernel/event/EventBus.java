package com.aiagent.kernel.event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
public class EventBus {
    private static final Logger log = LoggerFactory.getLogger(EventBus.class);
    private final List<AgentEventListener> listeners = new CopyOnWriteArrayList<>();
    public void register(AgentEventListener listener) {
        if (listener == null) throw new NullPointerException("listener must not be null");
        listeners.add(listener);
    }
    public boolean unregister(AgentEventListener listener) { return listeners.remove(listener); }
    public void publish(AgentEvent event) {
        if (event == null) throw new NullPointerException("event must not be null");
        for (AgentEventListener l : listeners) {
            if (l.supports(event.getClass())) {
                try { l.onEvent(event); } catch (Exception e) {
                    log.error("Listener threw exception: {}", e.getMessage(), e);
                }
            }
        }
    }
    public int listenerCount() { return listeners.size(); }
    public void clear() { listeners.clear(); }
}

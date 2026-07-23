package com.aiagent.kernel.model;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;
class AgentStepTest {
    @Test
    void factoryMethod_shouldCreateStepWithCurrentTimestamp() {
        var step = AgentStep.of(1, AgentStep.StepPhase.THINK, "thinking hard");
        assertEquals(1, step.stepNumber());
        assertEquals(AgentStep.StepPhase.THINK, step.phase());
        assertEquals("thinking hard", step.content());
        assertNotNull(step.timestamp());
    }
    @Test
    void constructor_shouldRejectNullPhase() {
        assertThrows(NullPointerException.class, () -> new AgentStep(1, null, "content", Instant.now()));
    }
    @Test
    void constructor_shouldRejectNullContent() {
        assertThrows(NullPointerException.class, () -> new AgentStep(1, AgentStep.StepPhase.ACT, null, Instant.now()));
    }
    @Test
    void constructor_shouldRejectNullTimestamp() {
        assertThrows(NullPointerException.class, () -> new AgentStep(1, AgentStep.StepPhase.OBSERVE, "obs", null));
    }
    @Test
    void constructor_shouldRejectInvalidStepNumber() {
        assertThrows(IllegalArgumentException.class, () -> new AgentStep(0, AgentStep.StepPhase.THINK, "x", Instant.now()));
        assertThrows(IllegalArgumentException.class, () -> new AgentStep(-1, AgentStep.StepPhase.THINK, "x", Instant.now()));
    }
    @Test
    void stepPhase_shouldHaveThreeValues() {
        assertEquals(3, AgentStep.StepPhase.values().length);
        assertEquals(AgentStep.StepPhase.THINK, AgentStep.StepPhase.valueOf("THINK"));
        assertEquals(AgentStep.StepPhase.ACT, AgentStep.StepPhase.valueOf("ACT"));
        assertEquals(AgentStep.StepPhase.OBSERVE, AgentStep.StepPhase.valueOf("OBSERVE"));
    }
    @Test
    void recordEquality_shouldWorkCorrectly() {
        var ts = Instant.now();
        var s1 = new AgentStep(1, AgentStep.StepPhase.THINK, "a", ts);
        var s2 = new AgentStep(1, AgentStep.StepPhase.THINK, "a", ts);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }
}

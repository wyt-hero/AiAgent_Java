package com.aiagent.kernel;
import com.aiagent.kernel.context.AgentContext;
import java.util.concurrent.CompletableFuture;
public interface AgentExecutor {
    Object execute(AgentContext context);
    default CompletableFuture<Object> executeAsync(AgentContext context) {
        return CompletableFuture.supplyAsync(() -> execute(context));
    }
    Agent agent();
}

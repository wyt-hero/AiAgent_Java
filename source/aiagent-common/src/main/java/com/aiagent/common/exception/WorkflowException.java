package com.aiagent.common.exception;
import java.util.Map;
public class WorkflowException extends BaseException {
    public WorkflowException(ErrorCode errorCode) { super(errorCode); }
    public WorkflowException(ErrorCode errorCode, String message) { super(errorCode, message); }
    public WorkflowException(ErrorCode errorCode, Throwable cause) { super(errorCode, cause); }
    public WorkflowException(ErrorCode errorCode, String message, Throwable cause) { super(errorCode, message, cause); }
    public WorkflowException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> context) { super(errorCode, message, cause, context); }
    public static WorkflowException executionFailed(String detail, Throwable cause) { return new WorkflowException(CommonErrorCode.WORKFLOW_EXECUTION_FAILED, CommonErrorCode.WORKFLOW_EXECUTION_FAILED.message().formatted(detail), cause); }
    public static WorkflowException nodeFailed(String nodeId, Throwable cause) { return new WorkflowException(CommonErrorCode.WORKFLOW_NODE_FAILED, CommonErrorCode.WORKFLOW_NODE_FAILED.message().formatted(nodeId), cause); }
    public static WorkflowException cycleDetected() { return new WorkflowException(CommonErrorCode.WORKFLOW_CYCLE_DETECTED); }
}
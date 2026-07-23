package com.aiagent.common.exception;
import java.util.Map;
public class ToolException extends BaseException {
    public ToolException(ErrorCode errorCode) { super(errorCode); }
    public ToolException(ErrorCode errorCode, String message) { super(errorCode, message); }
    public ToolException(ErrorCode errorCode, Throwable cause) { super(errorCode, cause); }
    public ToolException(ErrorCode errorCode, String message, Throwable cause) { super(errorCode, message, cause); }
    public ToolException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> context) { super(errorCode, message, cause, context); }
    public static ToolException notFound(String toolName) { return new ToolException(CommonErrorCode.TOOL_NOT_FOUND, CommonErrorCode.TOOL_NOT_FOUND.message().formatted(toolName)); }
    public static ToolException executionFailed(String toolName, Throwable cause) { return new ToolException(CommonErrorCode.TOOL_EXECUTION_FAILED, CommonErrorCode.TOOL_EXECUTION_FAILED.message().formatted(toolName), cause); }
    public static ToolException invalidParam(String paramName) { return new ToolException(CommonErrorCode.TOOL_PARAM_INVALID, CommonErrorCode.TOOL_PARAM_INVALID.message().formatted(paramName)); }
    public static ToolException permissionDenied(String toolName) { return new ToolException(CommonErrorCode.TOOL_PERMISSION_DENIED, CommonErrorCode.TOOL_PERMISSION_DENIED.message().formatted(toolName)); }
}
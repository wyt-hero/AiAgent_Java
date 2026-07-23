package com.aiagent.common.exception;
import java.util.Map;
public class PromptException extends BaseException {
    public PromptException(ErrorCode errorCode) { super(errorCode); }
    public PromptException(ErrorCode errorCode, String message) { super(errorCode, message); }
    public PromptException(ErrorCode errorCode, Throwable cause) { super(errorCode, cause); }
    public PromptException(ErrorCode errorCode, String message, Throwable cause) { super(errorCode, message, cause); }
    public PromptException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> context) { super(errorCode, message, cause, context); }
    public static PromptException renderFailed(String detail, Throwable cause) { return new PromptException(CommonErrorCode.PROMPT_RENDER_FAILED, CommonErrorCode.PROMPT_RENDER_FAILED.message().formatted(detail), cause); }
    public static PromptException tokenExceeded(int maxTokens, int actualTokens) { return new PromptException(CommonErrorCode.PROMPT_TOKEN_EXCEEDED, CommonErrorCode.PROMPT_TOKEN_EXCEEDED.message().formatted(maxTokens, actualTokens)); }
}
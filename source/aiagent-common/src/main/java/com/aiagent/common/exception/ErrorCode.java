package com.aiagent.common.exception;
public interface ErrorCode {
    int code();
    String message();
    default int httpStatus() { return 500; }
}
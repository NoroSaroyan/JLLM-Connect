package com.jllm.core.exception;

/**
 * Thrown when the request sent to the LLM provider is malformed or invalid.
 * <p>
 * This can be because of invalid parameters, missing required fields, or unsupported options.
 * </p>
 * author: @Noro
 * version: 1.0
 */
public class InvalidRequestException extends LLMException {
    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.jllm.core.exception;

/**
 * Thrown when authentication with the provider fails.
 * <p>
 * Usually caused by missing, invalid, or expired API tokens or credentials.
 * </p>
 * author: @Noro
 * version: 1.0
 */
public class AuthenticationException extends LLMException {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

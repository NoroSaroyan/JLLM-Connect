package com.jllm.core.exception;

/**
 * Thrown when a request exceeds the provider's rate limit.
 * <p>
 * This usually means that the client is sending too many requests
 * in a given amount of time. Backoff strategies or retry policies should be applied.
 * </p>
 * author: @Noro
 * version: 1.0
 */
public class RateLimitException extends LLMException {
    public RateLimitException(String message) {
        super(message);
    }

    public RateLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}

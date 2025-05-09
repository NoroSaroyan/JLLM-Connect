package com.jllm.core.exception;

/**
 * Thrown when the selected provider is not available or cannot be reached.
 * <p>
 * This can occur due to network issues, incorrect base URLs, provider downtime,
 * or misconfigurations in the SDK.
 * </p>
 * author: @Noro
 * version: 1.0
 */
public class ProviderNotAvailableException extends LLMException {
    public ProviderNotAvailableException(String message) {
        super(message);
    }

    public ProviderNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}

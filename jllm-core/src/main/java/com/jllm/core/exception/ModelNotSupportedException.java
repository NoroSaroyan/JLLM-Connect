package com.jllm.core.exception;

/**
 * Thrown when a requested model is not supported by the provider.
 * <p>
 * This may happen if the model name is incorrect, deprecated, or unavailable for the current plan.
 * </p>
 * author: @Noro
 * version: 1.0
 */
public class ModelNotSupportedException extends LLMException {
    public ModelNotSupportedException(String message) {
        super(message);
    }

    public ModelNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}


package com.jllm.core.exception;

/**
 * Base exception for all errors related to LLM operations.
 * <p>
 * This is the superclass of all LLM-related exceptions and can be used to catch
 * any generic failure that occurs while interacting with a language model provider.
 * </p>
 */
public class LLMException extends RuntimeException {
    public LLMException(String message) {
        super(message);
    }

    public LLMException(String message, Throwable cause) {
        super(message, cause);
    }
}

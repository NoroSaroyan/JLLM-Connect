package com.jllm.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents the token usage statistics for an LLM request.
 * This class includes information about prompt, completion, and total token usage.
 *
 * <p>Example usage:
 * <pre>
 *     TokenUsage usage = response.getUsage();
 *     System.out.println("Prompt Tokens: " + usage.getPromptTokens());
 * </pre>
 * </p>
 *
 * Author: @Noro
 */
public final class TokenUsage {

    @JsonProperty("promptTokens")
    private final int promptTokens;

    @JsonProperty("completionTokens")
    private final int completionTokens;

    @JsonProperty("totalTokens")
    private final int totalTokens;

    /**
     * Constructor to initialize token usage fields.
     *
     * @param promptTokens The number of tokens used in the prompt.
     * @param completionTokens The number of tokens used in the completion.
     * @param totalTokens The total number of tokens used.
     */
    public TokenUsage(
            @JsonProperty("promptTokens") int promptTokens,
            @JsonProperty("completionTokens") int completionTokens,
            @JsonProperty("totalTokens") int totalTokens
    ) {
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
    }

    public int getPromptTokens() {
        return promptTokens;
    }

    public int getCompletionTokens() {
        return completionTokens;
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenUsage)) return false;
        TokenUsage that = (TokenUsage) o;
        return promptTokens == that.promptTokens &&
               completionTokens == that.completionTokens &&
               totalTokens == that.totalTokens;
    }

    @Override
    public int hashCode() {
        return Objects.hash(promptTokens, completionTokens, totalTokens);
    }

    @Override
    public String toString() {
        return "TokenUsage{" +
               "promptTokens=" + promptTokens +
               ", completionTokens=" + completionTokens +
               ", totalTokens=" + totalTokens +
               '}';
    }
}

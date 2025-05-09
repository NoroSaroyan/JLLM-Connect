package com.jllm.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * Represents the response from an LLM after processing a prompt request.
 * This class contains the output text, token usage information, and log probabilities.
 *
 * <p>Example usage:
 * <pre>
 *     PromptResponse response = apiClient.sendPrompt(request);
 *     System.out.println(response.getText());
 * </pre>
 * </p>
 * <p>
 * Author: @Noro
 * Version: 1.0
 */
public final class PromptResponse {

    @Getter
    @JsonProperty("choices")
    private final String text;

    @Getter
    @JsonProperty("usage")
    private final TokenUsage usage;

    @JsonProperty("logprobs")
    private final List<LogProb> logProbs;

    /**
     * Private constructor to ensure objects are created using the Builder pattern.
     *
     * @param b The builder instance.
     */
    private PromptResponse(Builder b) {
        this.text = Objects.requireNonNull(b.text);
        this.usage = Objects.requireNonNull(b.usage);
        this.logProbs = (b.logProbs == null)
                ? List.of()
                : List.copyOf(b.logProbs);
    }

    public List<LogProb> getLogProbs() {
        return logProbs == null ? null : List.copyOf(logProbs);
    }

    public static final class Builder {
        private String text;
        private TokenUsage usage;
        private List<LogProb> logProbs;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder usage(TokenUsage usage) {
            this.usage = usage;
            return this;
        }

        public Builder logProbs(List<LogProb> logProbs) {
            this.logProbs = logProbs;
            return this;
        }

        /**
         * Validates required fields and returns a new {@link PromptResponse} instance.
         *
         * @return The new {@link PromptResponse} instance.
         * @throws NullPointerException if any required field is null.
         */
        public PromptResponse build() {
            Objects.requireNonNull(text, "text cannot be null");
            Objects.requireNonNull(usage, "usage cannot be null");
            return new PromptResponse(this);
        }
    }
}

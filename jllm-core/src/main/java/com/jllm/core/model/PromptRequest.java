package com.jllm.core.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a request for a prompt to an LLM (Large Language Model).
 * This class is used to structure the input for the prompt, including model choice,
 * messages, temperature, and other parameters.
 *
 * <p>Example usage:
 * <pre>
 *     PromptRequest request = PromptRequest.builder()
 *         .model("text-davinci-003")
 *         .messages(messagesList)
 *         .temperature(0.7)
 *         .maxTokens(100)
 *         .stopSequences(stopSequencesList)
 *         .build();
 * </pre>
 * </p>
 * <p>
 * Author: @Noro
 * Version: 1.0
 */
@Getter
public final class PromptRequest {

    @NonNull
    private final String model;

    @NonNull
    private final List<Message> messages;

    private final double temperature;
    private final int maxTokens;
    private final String user;
    private final List<String> stopSequences;

    /**
     * Private constructor to ensure that objects are created via the Builder pattern.
     *
     * @param builder The builder instance.
     */
    private PromptRequest(Builder builder) {
        this.model = builder.model;
        this.messages = builder.messages;
        this.temperature = builder.temperature;
        this.maxTokens = builder.maxTokens;
        this.user = builder.user;
        this.stopSequences = builder.stopSequences;
    }

    /**
     * Create a new PromptRequest with the provided model, keeping the other fields unchanged.
     *
     * @param model the new model to set
     * @return a new PromptRequest with the updated model
     */
    public PromptRequest withModel(String model) {
        return builder()
                .model(model)
                .messages(this.messages)
                .temperature(this.temperature)
                .maxTokens(this.maxTokens)
                .user(this.user)
                .stopSequences(this.stopSequences)
                .build();
    }

    /**
     * Returns a new builder instance for constructing a {@link PromptRequest}.
     *
     * @return A new builder instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder pattern to construct a PromptRequest.
     */
    public static class Builder {

        private String model;
        private List<Message> messages = new ArrayList<>();
        private double temperature = 1.0;
        private int maxTokens = 256;
        private String user;
        private List<String> stopSequences = new ArrayList<>();

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder messages(List<Message> messages) {
            this.messages = messages;
            return this;
        }

        public Builder temperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder maxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder stopSequences(List<String> stopSequences) {
            this.stopSequences = stopSequences;
            return this;
        }

        /**
         * Validates required fields and returns a new {@link PromptRequest} instance.
         *
         * @return The new {@link PromptRequest} instance.
         * @throws NullPointerException if any required field is null.
         */
        public PromptRequest build() {
            Objects.requireNonNull(model, "model cannot be null");
            if (messages == null || messages.isEmpty()) {
                throw new NullPointerException("messages cannot be null or empty");
            }
            return new PromptRequest(this);
        }
    }
}

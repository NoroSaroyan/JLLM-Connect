package com.jllm.core.model;

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
 *
 * Author: @Noro
 * Version: 1.0
 */
public final class PromptRequest {

    private final String model;
    private final List<String> messages;
    private final double temperature;
    private final int maxTokens;
    private final String user;
    private final List<String> stopSequences;

    /**
     * Private constructor to ensure that objects are created via the Builder pattern.
     *
     * @param b The builder instance.
     */
    private PromptRequest(Builder b) {
        this.model = b.model;
        this.messages = List.copyOf(b.messages);
        this.temperature = b.temperature;
        this.maxTokens = b.maxTokens;
        this.user = b.user;
        this.stopSequences = List.copyOf(b.stopSequences);
    }

    /**
     * Create a new PromptRequest with the provided model, keeping the other fields unchanged.
     *
     * @param model the new model to set
     * @return a new PromptRequest with the updated model
     */
    public PromptRequest withModel(String model) {
        return new Builder()
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

    public static final class Builder {

        private String model;
        private List<String> messages = new ArrayList<>();
        private double temperature = 1.0;
        private int maxTokens = 256;
        private String user;
        private List<String> stopSequences = new ArrayList<>();

        public Builder model(String m) {
            this.model = m;
            return this;
        }

        public Builder messages(List<String> msgs) {
            this.messages = msgs;
            return this;
        }

        public Builder temperature(double t) {
            this.temperature = t;
            return this;
        }

        public Builder maxTokens(int m) {
            this.maxTokens = m;
            return this;
        }

        public Builder user(String u) {
            this.user = u;
            return this;
        }

        public Builder stopSequences(List<String> stops) {
            this.stopSequences = stops;
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
            Objects.requireNonNull(messages, "messages cannot be null");
            return new PromptRequest(this);
        }
    }

    public String getModel() {
        return model;
    }

    public List<String> getMessages() {
        return messages;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public String getUser() {
        return user;
    }

    public List<String> getStopSequences() {
        return stopSequences;
    }
}

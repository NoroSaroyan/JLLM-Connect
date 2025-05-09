package com.jllm.core.client;


import com.jllm.core.model.PromptRequest;
import com.jllm.core.model.PromptResponse;
import com.jllm.core.provider.Provider;
import com.jllm.core.provider.StreamingListener;
import com.jllm.core.rate.RateLimiter;
import com.jllm.core.retry.RetryPolicy;

import java.io.IOException;
import java.util.Objects;

/**
 * Single entry point for synchronous and streaming LLM calls.
 * Immutable and thread-safe.
 * <p>
 * Author: @Noro
 */
public final class LLMClient {

    private final com.jllm.core.provider.Provider provider;
    private final String model;
    private final RetryPolicy retryPolicy;
    private final RateLimiter rateLimiter;

    private LLMClient(Builder builder) {
        this.provider = builder.provider;
        this.model = builder.model;
        this.retryPolicy = builder.retryPolicy;
        this.rateLimiter = builder.rateLimiter;
    }

    /**
     * Send a synchronous prompt request.
     */
    public PromptResponse send(PromptRequest request) throws InterruptedException, IOException {
        Objects.requireNonNull(request, "PromptRequest cannot be null");
        rateLimiter.acquire();                                   // token-bucket check
        return retryPolicy.execute(() -> {
            try {
                return provider.execute(
                        request.withModel(model)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Send a streaming prompt; tokens delivered via listener.
     */
    public void sendStream(PromptRequest request, StreamingListener listener) throws InterruptedException {
        Objects.requireNonNull(listener, "StreamingListener cannot be null");
        rateLimiter.acquire();
        retryPolicy.executeVoid(() -> {
            try {
                provider.executeStream(
                        request.withModel(model), listener
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Builder for LLMClient (Joshua Bloch style).
     */
    public static class Builder {
        private Provider provider;
        private String model;
        private RetryPolicy retryPolicy = RetryPolicy.defaultPolicy();
        private RateLimiter rateLimiter = RateLimiter.createDefault();

        /**
         * Specify which LLM provider to use.
         */
        public Builder provider(Provider p) {
            this.provider = p;
            return this;
        }

        /**
         * Specify model name (e.g. "gpt-4").
         */
        public Builder model(String m) {
            this.model = m;
            return this;
        }

        /**
         * Customize retry policy (exponential backoff).
         */
        public Builder retryPolicy(RetryPolicy rp) {
            this.retryPolicy = rp;
            return this;
        }

        /**
         * Customize rate limiter (token bucket).
         */
        public Builder rateLimiter(RateLimiter rl) {
            this.rateLimiter = rl;
            return this;
        }

        /**
         * Build the immutable LLMClient instance.
         */
        public LLMClient build() {
            Objects.requireNonNull(model, "Model must be set");
            return new LLMClient(this);
        }
    }

    /**
     * Create a new Builder.
     */
    public static Builder builder() {
        return new Builder();
    }
}

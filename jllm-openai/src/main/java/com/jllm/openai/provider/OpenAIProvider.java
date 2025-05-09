package com.jllm.openai.provider;


import com.jllm.core.model.LogProb;
import com.jllm.core.model.PromptRequest;
import com.jllm.core.model.PromptResponse;
import com.jllm.core.model.TokenUsage;
import com.jllm.core.provider.Provider;
import com.jllm.core.retry.RetryPolicy;
import com.jllm.core.provider.StreamingListener;

import java.util.List;

/**
 * Demo Provider for OpenAI.
 * <p>
 * This class is a temporary implementation for demonstration purposes.
 * Replace with actual OpenAI API integration once implemented.
 *
 * Author: @Noro
 */
public class OpenAIProvider implements Provider {

    private static final String API_URL = "https://api.openai.com/v1/completions";

    /**
     * Sends a request to the LLM provider with the given prompt request.
     * The method should return a {@link PromptResponse} containing the model's response to the prompt.
     *
     * @param promptRequest The request containing the model, messages, and parameters for the LLM.
     * @return A {@link PromptResponse} containing the model's response.
     */
    @Override
    public PromptResponse sendRequest(PromptRequest promptRequest) {
        return null;
    }

    /**
     * Checks whether the LLM provider is available to receive requests.
     * This can be used to verify that the API is reachable before sending a request.
     *
     * @return true if the provider's API is available; false otherwise.
     */
    @Override
    public boolean isAvailable() {
        return false;
    }

    /**
     * Sets the API authentication token required to make requests to the LLM provider.
     *
     * @param token The API token for authentication with the provider.
     */
    @Override
    public void setAuthToken(String token) {

    }

    /**
     * Retrieves the base API URL of the LLM provider.
     *
     * @return The base API URL for the provider's API.
     */
    @Override
    public String getApiUrl() {
        return null;
    }

    @Override
    public PromptResponse execute(PromptRequest request) {
        // Simulated response for demo purposes
        System.out.println("Executing synchronous request to OpenAI with model: " + request.getModel());

        String responseText = "This is a simulated response from OpenAI model " + request.getModel();
        TokenUsage usage = new TokenUsage(100, 150, 250);
        List<LogProb> logProbs = null;

        return new PromptResponse.Builder()
                .text(responseText)
                .usage(usage)
                .logProbs(logProbs)
                .build();
    }

    @Override
    public void executeStream(PromptRequest request, StreamingListener listener) {
        // Simulated streaming for demo purposes
        System.out.println("Starting streaming request to OpenAI with model: " + request.getModel());

        String[] fakeStreamTokens = {"This", "is", "a", "streaming", "response", "from", "OpenAI"};

        for (String token : fakeStreamTokens) {
            try {
                Thread.sleep(500);  // Simulating network delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            listener.onTokenReceived(token);
        }

        listener.onStreamComplete();
    }
}

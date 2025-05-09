package com.jllm.core.provider;

import com.jllm.core.model.PromptRequest;
import com.jllm.core.model.PromptResponse;

import java.io.IOException;

/**
 * The Provider interface represents a contract for interacting with various Large Language Model (LLM) providers.
 * It defines essential methods for sending requests to LLMs and receiving responses, allowing the SDK to work
 * with different LLM APIs such as OpenAI, Hugging Face, etc. The Provider interface provides flexibility for
 * switching between different providers without altering the application code.
 * <p>
 * Implementing classes should handle the specifics of authentication, request formatting, and response parsing
 * according to the requirements of each provider's API.
 * </p>
 *
 * <h3>Methods</h3>
 * <ul>
 *     <li>{@link #sendRequest(com.jllm.core.model.PromptRequest)}: Sends a request to the LLM provider and returns a response.</li>
 *     <li>{@link #isAvailable()}: Checks whether the LLM provider's API is available for requests.</li>
 *     <li>{@link #setAuthToken(String)}: Sets the API authentication token for the LLM provider.</li>
 *     <li>{@link #getApiUrl()}: Retrieves the base API URL of the provider.</li>
 * </ul>
 *
 * @author @Noro
 * @version 1.0
 */
public interface Provider {

    /**
     * Sends a request to the LLM provider with the given prompt request.
     * The method should return a {@link PromptResponse} containing the model's response to the prompt.
     *
     * @param promptRequest The request containing the model, messages, and parameters for the LLM.
     * @return A {@link PromptResponse} containing the model's response.
     */
    PromptResponse sendRequest(PromptRequest promptRequest);

    /**
     * Checks whether the LLM provider is available to receive requests.
     * This can be used to verify that the API is reachable before sending a request.
     *
     * @return true if the provider's API is available; false otherwise.
     */
    boolean isAvailable();

    /**
     * Sets the API authentication token required to make requests to the LLM provider.
     *
     * @param token The API token for authentication with the provider.
     */
    void setAuthToken(String token);

    /**
     * Retrieves the base API URL of the LLM provider.
     *
     * @return The base API URL for the provider's API.
     */
    String getApiUrl();

    /**
     * Executes a synchronous prompt request to the provider.
     *
     * @param request The prompt request.
     * @return The response from the provider.
     * @throws IOException If there is an issue with the request.
     */
    PromptResponse execute(PromptRequest request) throws IOException;

    /**
     * Executes an asynchronous streaming prompt request to the provider.
     * The provided listener will handle tokens as they are delivered.
     *
     * @param request  The prompt request.
     * @param listener The listener that processes each token.
     * @throws IOException If there is an issue with the request.
     */
    void executeStream(PromptRequest request, StreamingListener listener) throws IOException;
}

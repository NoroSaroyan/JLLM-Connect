package com.jllm.core.provider;

import com.jllm.core.model.PromptResponse;

/**
 * The StreamingListener interface defines methods for handling real-time streaming responses from LLM providers.
 * Some LLM providers support streaming responses, where the model generates text in multiple chunks over time.
 * This interface allows the client to listen and react to these chunks as they arrive.
 * <p>
 * Implementations of this interface should handle the streaming events such as the start of streaming, receiving
 * new data chunks, and detecting when the streaming ends. It should also handle any errors that occur during streaming.
 * </p>
 *
 * <h3>Methods</h3>
 * <ul>
 *     <li>{@link #onStart()}: Called when streaming starts.</li>
 *     <li>{@link #onData(String)}: Called whenever a new chunk of data is received.</li>
 *     <li>{@link #onEnd()}: Called when the streaming process ends.</li>
 *     <li>{@link #onError(Exception)}: Called if an error occurs during the streaming process.</li>
 * </ul>
 *
 * @author @Noro
 * @version 1.0
 */
public interface StreamingListener {

    /**
     * Called when the streaming process begins.
     * This method allows clients to prepare for receiving streaming data.
     */
    void onStart();

    /**
     * Called whenever a new chunk of data is received during streaming.
     *
     * @param chunk A string representing a chunk of data from the LLM.
     */
    void onData(String chunk);

    /**
     * Called when the streaming process finishes.
     * This method allows clients to handle post-streaming actions, such as cleanup or final processing.
     */
    void onEnd();

    /**
     * Called if an error occurs during streaming.
     *
     * @param e The exception that occurred during streaming.
     */
    void onError(Exception e);

     /**
     * This method is called for each token delivered during the streaming process.
     * The provider will call this method as the token stream progresses.
     *
     * @param token The token returned by the LLM provider.
     */
    void onTokenReceived(String token);

    /**
     * This method is called when the streaming operation completes, either successfully or with an error.
     *
     * @param response The final response from the provider, if any.
     * @param error An optional error that occurred during streaming.
     */
    void onStreamCompleted(PromptResponse response, Exception error);

    void onStreamComplete();
}

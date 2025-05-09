package com.jllm.core.retry;

import java.util.function.Supplier;

/**
 * A retry policy that defines how failed requests should be retried.
 * Supports exponential backoff or simple retry strategies.
 *
 * Author: @Noro
 */
public class RetryPolicy {

    private final int maxRetries;
    private final long delayMs;

    /**
     * Constructs a RetryPolicy with a maximum number of retries and a delay between retries.
     *
     * @param maxRetries The maximum number of retry attempts.
     * @param delayMs The delay between retries in milliseconds.
     */
    public RetryPolicy(int maxRetries, long delayMs) {
        this.maxRetries = maxRetries;
        this.delayMs = delayMs;
    }

    /**
     * Executes a task with retry logic. If the task fails, it will be retried up to the maximum retries.
     *
     * @param task The task to execute.
     * @param <T> The return type of the task.
     * @return The result of the task execution.
     * @throws InterruptedException If the thread is interrupted during a retry.
     */
    public <T> T execute(Supplier<T> task) throws InterruptedException {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                return task.get();
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw e;
                }
                Thread.sleep(delayMs * attempt); // Exponential backoff (or simple delay)
            }
        }
        throw new IllegalStateException("Max retries reached");
    }

    /**
     * Executes a void task with retry logic.
     *
     * @param task The task to execute.
     * @throws InterruptedException If the thread is interrupted during a retry.
     */
    public void executeVoid(Runnable task) throws InterruptedException {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                task.run();
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw e;
                }
                Thread.sleep(delayMs * attempt); // Exponential backoff (or simple delay)
            }
        }
        throw new IllegalStateException("Max retries reached");
    }

    /**
     * Default retry policy with 3 retries and a 1-second delay.
     */
    public static RetryPolicy defaultPolicy() {
        return new RetryPolicy(3, 1000);
    }
}

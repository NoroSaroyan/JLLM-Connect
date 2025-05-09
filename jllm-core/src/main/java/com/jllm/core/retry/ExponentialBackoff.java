package com.jllm.core.retry;

/**
 * ExponentialBackoff implements the exponential backoff strategy for retrying operations
 * that might fail intermittently, such as interacting with an external service or API.
 * <p>
 * Exponential backoff helps prevent overwhelming the target service by exponentially increasing
 * the wait time between retry attempts. It is often used to handle rate-limiting errors,
 * network timeouts, or temporary unavailability of the service.
 * </p>
 * <h3>Parameters:</h3>
 * <ul>
 *     <li>Initial delay: The initial wait time before retrying.</li>
 *     <li>Maximum delay: The upper bound on the time between retries.</li>
 *     <li>Backoff factor: A factor by which the delay increases after each failed attempt.</li>
 *     <li>Max attempts: The maximum number of retry attempts before giving up.</li>
 * </ul>
 * <p>
 * This class provides a method to execute a task with automatic retries, applying exponential
 * backoff when failures occur.
 * </p>
 * <h3>Usage:</h3>
 * <pre>
 *     ExponentialBackoff backoff = new ExponentialBackoff(1000, 30000, 5, 2.0);
 *     backoff.executeWithBackoff(() -> {
 *         // Task logic (e.g., making an API request)
 *     });
 * </pre>
 *
 * @author @Noro
 * @version 1.0
 */
public class ExponentialBackoff {
    private final int initialDelayMillis; // Initial delay before retry
    private final int maxDelayMillis; // Maximum delay
    private final int maxAttempts; // Maximum number of retry attempts
    private final double backoffFactor; // Factor by which to multiply delay on each retry

    /**
     * Creates a new instance of ExponentialBackoff with the specified parameters.
     *
     * @param initialDelayMillis The initial delay before retrying (in milliseconds).
     * @param maxDelayMillis The maximum delay between retries (in milliseconds).
     * @param maxAttempts The maximum number of retry attempts before giving up.
     * @param backoffFactor The factor by which to multiply delay after each failure.
     */
    public ExponentialBackoff(int initialDelayMillis, int maxDelayMillis, int maxAttempts, double backoffFactor) {
        this.initialDelayMillis = initialDelayMillis;
        this.maxDelayMillis = maxDelayMillis;
        this.maxAttempts = maxAttempts;
        this.backoffFactor = backoffFactor;
    }

    /**
     * Retries the given task with exponential backoff.
     * <p>
     * If the task fails, the delay between retries will increase exponentially,
     * with a maximum cap on the delay.
     * </p>
     *
     * @param task The task to retry, which can throw an exception on failure.
     * @throws InterruptedException if the thread is interrupted.
     */
    public void executeWithBackoff(Runnable task) throws InterruptedException {
        int attempts = 0;
        long delay = initialDelayMillis;

        while (attempts < maxAttempts) {
            try {
                // Attempt to execute the task
                task.run();
                return; // If the task succeeds, exit the method
            } catch (Exception e) {
                attempts++;
                if (attempts >= maxAttempts) {
                    throw new RuntimeException("Max retry attempts reached", e);
                }

                // Sleep for the current delay before retrying
                Thread.sleep(delay);

                // Exponentially increase the delay, but ensure it doesn't exceed maxDelayMillis
                delay = Math.min((long) (delay * backoffFactor), maxDelayMillis);
            }
        }
    }
}

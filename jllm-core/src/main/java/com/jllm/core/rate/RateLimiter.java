package com.jllm.core.rate;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A RateLimiter controls the rate of operations, ensuring that a certain limit on requests is not exceeded within a given time period.
 * It is thread-safe and can be used to throttle requests to LLM services or any other resource.
 * <p>
 * This RateLimiter uses a simple sliding window mechanism to track the number of requests made in a specific time frame.
 *
 * @author @Noro
 * @version 1.0
 */
public class RateLimiter {

    // Time window in milliseconds
    private final long windowTime;
    // Maximum number of allowed requests in the time window
    private final int maxRequests;
    // Atomic counter to track the number of requests
    private final AtomicInteger requestCount;
    // Lock to synchronize access to the request count
    private final ReentrantLock lock;

    // Timestamp of the last reset (start of the time window)
    private long windowStartTime;

    /**
     * Constructs a RateLimiter with the specified request limit and time window.
     *
     * @param maxRequests The maximum number of requests allowed in the time window.
     * @param windowTime  The time window in milliseconds.
     */
    public RateLimiter(int maxRequests, long windowTime) {
        this.maxRequests = maxRequests;
        this.windowTime = windowTime;
        this.requestCount = new AtomicInteger(0);
        this.lock = new ReentrantLock();
        this.windowStartTime = System.currentTimeMillis();
    }

    /**
     * Acquires a permit for a request. If the rate limit has been reached, this method will block until a request can be made.
     *
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void acquire() throws InterruptedException {
        // Ensure thread-safe access to request count
        lock.lock();
        try {
            long currentTime = System.currentTimeMillis();
            if (currentTime - windowStartTime > windowTime) {
                // Reset the window if the time window has passed
                resetWindow();
            }

            // Check if the request count exceeds the maximum allowed requests
            while (requestCount.get() >= maxRequests) {
                // If exceeded, wait until the next time window
                long waitTime = windowStartTime + windowTime - currentTime;
                if (waitTime > 0) {
                    Thread.sleep(waitTime);
                }
                currentTime = System.currentTimeMillis();
                resetWindow();
            }

            // Increment request count as the request is allowed
            requestCount.incrementAndGet();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Resets the rate limit window.
     */
    private void resetWindow() {
        windowStartTime = System.currentTimeMillis();
        requestCount.set(0);
    }

    /**
     * Gets the current number of requests made in the current time window.
     *
     * @return The current request count.
     */
    public int getRequestCount() {
        return requestCount.get();
    }


    /**
     * Create a default RateLimiter with a standard configuration.
     *
     * @return The default RateLimiter.
     */
    public static RateLimiter createDefault() {
        return new RateLimiter(5, 10); // Default: 5 requests/second, 10 burst capacity.
    }
}

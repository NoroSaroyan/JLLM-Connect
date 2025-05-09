package com.jllm.core.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * Represents the log probabilities for a particular token in the LLM completion.
 * This can be useful to analyze which tokens are most probable and understand
 * the model's behavior better.
 *
 * <p>Example usage:</p>
 * <pre>
 *     LogProb logProb = new LogProb("hello", -2.3, topLogProbsList);
 *     System.out.println(logProb.getToken());
 *     System.out.println(logProb.getLogProb());
 * </pre>
 *
 * Author: @Noro
 */
public final class LogProb {

    @JsonProperty("token")
    private final String token;

    @JsonProperty("logProb")
    private final double logProb;

    @JsonProperty("topLogProbs")
    private final List<Double> topLogProbs;

    /**
     * Constructor to initialize a LogProb instance.
     *
     * @param token The token string (e.g., a word, subword, or character).
     * @param logProb The log probability of the token.
     * @param topLogProbs A list of top log probabilities for the token.
     */
    public LogProb(
            @JsonProperty("token") String token,
            @JsonProperty("logProb") double logProb,
            @JsonProperty("topLogProbs") List<Double> topLogProbs
    ) {
        this.token = Objects.requireNonNull(token, "Token cannot be null");
        this.logProb = logProb;
        this.topLogProbs = topLogProbs == null ? null : List.copyOf(topLogProbs);
    }

    /**
     * @return The token string.
     */
    public String getToken() {
        return token;
    }

    /**
     * @return The log probability.
     */
    public double getLogProb() {
        return logProb;
    }

    /**
     * @return A list of top log probabilities.
     */
    public List<Double> getTopLogProbs() {
        return topLogProbs == null ? null : List.copyOf(topLogProbs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogProb)) return false;
        LogProb logProb1 = (LogProb) o;
        return Double.compare(logProb1.getLogProb(), getLogProb()) == 0 &&
               Objects.equals(getToken(), logProb1.getToken()) &&
               Objects.equals(getTopLogProbs(), logProb1.getTopLogProbs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken(), getLogProb(), getTopLogProbs());
    }

    @Override
    public String toString() {
        return "LogProb{" +
                "token='" + token + '\'' +
                ", logProb=" + logProb +
                ", topLogProbs=" + topLogProbs +
                '}';
    }
}

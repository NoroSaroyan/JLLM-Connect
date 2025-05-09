package com.jllm.core.provider;

import com.jllm.openai.provider.OpenAIProvider;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * The ProviderFactory class creates instances of the LLM provider based on the provided provider type.
 * The factory uses a map to dynamically associate provider types with their respective classes.
 * This implementation is extensible and avoids hardcoding the provider creation logic using a switch or if-else statements.
 *
 * @author @Noro
 * @version 1.0
 */
public class ProviderFactory {

    // Map to hold provider type and their corresponding provider supplier
    private static final Map<String, Supplier<Provider>> providerMap = new HashMap<>();

    // Static block to initialize the provider map
    static {
        providerMap.put("openai", OpenAIProvider::new);
//        providerMap.put("huggingface", HuggingFaceProvider::new);
        // Add new providers here easily, without modifying the createProvider method
    }

    /**
     * Creates a provider instance based on the specified provider type.
     * The method looks up the provider in the provider map and creates an instance of it using the associated supplier.
     *
     * @param providerType The type of provider to create (e.g., "OpenAI", "HuggingFace").
     * @return A {@link Provider} instance corresponding to the requested provider type.
     * @throws IllegalArgumentException If the provider type is unsupported.
     */
    public static Provider createProvider(String providerType) {
        // Normalize the providerType to lowercase for case-insensitive matching
        Supplier<Provider> providerSupplier = providerMap.get(providerType.toLowerCase());

        if (providerSupplier == null) {
            throw new IllegalArgumentException("Unknown provider type: " + providerType);
        }

        return providerSupplier.get();
    }

    /**
     * Load and return the default LLM provider.
     * The default provider could be chosen based on environment variables,
     * configuration settings, or hard-coded preferences.
     *
     * @return The default provider (e.g., OpenAIProvider, HuggingFaceProvider).
     */
    public static Provider loadDefault() {
        // Here we are just choosing OpenAI as the default provider.
        // You can modify this logic to choose based on configuration or environment settings.

        // This can be replaced with more dynamic logic or configurations.
        return new OpenAIProvider(); // Example, replace with your actual logic
    }
}

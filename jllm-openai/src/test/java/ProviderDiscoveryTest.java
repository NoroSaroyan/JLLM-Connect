
import com.jllm.core.provider.Provider;
import com.jllm.openai.provider.OpenAIProvider;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProviderDiscoveryTest {
//    @Test
//    void shouldDiscoverAllProviders() {
//        ServiceLoader<Provider> loader = ServiceLoader.load(Provider.class);
//        List<Provider> providers = loader.stream()
//                .map(ServiceLoader.Provider::get)
//                .collect(Collectors.toList());
//
////        assertTrue(providers.isEmpty(), "No providers were discovered.");
//        // Optionally, assert specific providers are present
//         assertTrue(providers.stream().anyMatch(p -> p instanceof OpenAIProvider));
//    }
}

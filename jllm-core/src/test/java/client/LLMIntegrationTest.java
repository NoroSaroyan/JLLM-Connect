package client;

import com.jllm.core.client.LLMClient;
import com.jllm.core.model.Message;
import com.jllm.core.model.PromptRequest;
import com.jllm.core.model.PromptResponse;
import com.jllm.core.model.TokenUsage;
import com.jllm.core.provider.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LLMClientIntegrationTest {

    @Mock
    private Provider mockProvider;

    private LLMClient client;

    @BeforeEach
    void setUp() {
        client = LLMClient.builder()
                .provider(mockProvider)
                .model("gpt-4")
                .build();
    }

    @Test
    void testEndToEndFlow() throws IOException, InterruptedException {
        PromptRequest request = PromptRequest.builder()
                .temperature(0.7)
                .messages(List.of(new Message("user", "Hello")))
                .model("gpt-4")
                .build();
        PromptResponse mockResponse = new PromptResponse.Builder().text("Hi there!").usage(new TokenUsage(10, 10, 20)).build();
        when(mockProvider.sendRequest(request)).thenReturn(mockResponse);

        PromptResponse response = client.send(request);

        assertNotNull(response);
        assertEquals("Hi there!", response.getText());
    }
}

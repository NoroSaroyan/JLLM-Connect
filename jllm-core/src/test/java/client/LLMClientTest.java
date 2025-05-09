// src/test/java/com/jllm/core/client/LLMClientTest.java
package client;

import com.jllm.core.client.LLMClient;
import com.jllm.core.model.Message;
import com.jllm.core.model.PromptRequest;
import com.jllm.core.model.PromptResponse;
import com.jllm.core.model.TokenUsage;
import com.jllm.core.provider.Provider;
import com.jllm.core.provider.StreamingListener;
import com.jllm.core.rate.RateLimiter;
import com.jllm.core.retry.RetryPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LLMClientTest {

    @Mock
    Provider mockProvider;
    @Mock
    RateLimiter mockLimiter;
    @Mock
    RetryPolicy mockRetry;

    private LLMClient client;
    private PromptRequest baseReq;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = LLMClient.builder()
                .provider(mockProvider)
                .model("test-model")
                .rateLimiter(mockLimiter)
                .retryPolicy(mockRetry)
                .build();
        baseReq = PromptRequest.builder()
                .model("ignored")
                .messages(List.of(new Message("user", "hi")))
                .build();
    }

    @Test
    void send_happyPath_returnsResponse() throws Exception {
        PromptResponse mockResponse = new PromptResponse.Builder()
                .text("ok")
                .usage(new TokenUsage(1, 1, 2))
                .build();

        // stub rateLimiter and retry to invoke the supplier
        doNothing().when(mockLimiter).acquire();
        when(mockRetry.execute(any()))
                .thenAnswer(inv -> ((java.util.function.Supplier<?>) inv.getArgument(0)).get());

        // *** Stub the correct method – provider.execute(...) not sendRequest(...) ***
        when(mockProvider.execute(baseReq.withModel("test-model")))
                .thenReturn(mockResponse);

        PromptResponse out = client.send(baseReq);

        assertSame(mockResponse, out);

        InOrder o = inOrder(mockLimiter, mockRetry, mockProvider);
        o.verify(mockLimiter).acquire();
        o.verify(mockRetry).execute(any());
        o.verify(mockProvider).execute(baseReq.withModel("test-model"));
    }

    @Test
    void send_providerIOException_wrappedAsRuntime() throws Exception {
        doNothing().when(mockLimiter).acquire();
        // let retryPolicy.wrap exceptions naturally
        when(mockRetry.execute(any())).thenAnswer(inv -> {
            try {
                return ((java.util.function.Supplier<?>) inv.getArgument(0)).get();
            } catch (RuntimeException re) {
                throw re;
            }
        });
        // *** Throw from execute(...) ***
        when(mockProvider.execute(any()))
                .thenThrow(new IOException("boom"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> client.send(baseReq));
        assertTrue(ex.getCause() instanceof IOException);
    }

    @Test
    void sendStream_happyPath_callbacksInvoked() throws Exception {
        PromptResponse finalResp = new PromptResponse.Builder()
                .text("done")
                .usage(new TokenUsage(1, 1, 2))
                .build();

        doNothing().when(mockLimiter).acquire();
        // stub retryPolicy.executeVoid(...) (the method LLMClient actually calls)
        doNothing().when(mockRetry).executeVoid(any());

        // when executeStream is called, drive the listener
        doAnswer(inv -> {
            StreamingListener listener = inv.getArgument(1);
            listener.onStart();
            listener.onTokenReceived("t1");
            listener.onData("c1");
            listener.onEnd();
            listener.onStreamCompleted(finalResp, null);
            listener.onStreamComplete();
            return null;
        }).when(mockProvider).executeStream(any(), any());

        StreamingListener listener = mock(StreamingListener.class);
        client.sendStream(baseReq, listener);

        InOrder o = inOrder(listener);
        o.verify(listener).onStart();
        o.verify(listener).onTokenReceived("t1");
        o.verify(listener).onData("c1");
        o.verify(listener).onEnd();
        o.verify(listener).onStreamCompleted(finalResp, null);
        o.verify(listener).onStreamComplete();
    }

    @Test
    void builder_missingModel_throwsNPE() {
        LLMClient.Builder b = LLMClient.builder().provider(mockProvider);
        NullPointerException ex = assertThrows(NullPointerException.class, b::build);
        assertEquals("Model must be set", ex.getMessage());
    }

}

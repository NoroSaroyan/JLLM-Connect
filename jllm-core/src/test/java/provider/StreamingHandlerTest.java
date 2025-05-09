package provider;

import com.jllm.core.model.PromptResponse;
import com.jllm.core.model.TokenUsage;
import com.jllm.core.provider.StreamingListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StreamingListenerTest {

    @Mock
    private StreamingListener mockListener;  // mock your actual interface :contentReference[oaicite:0]{index=0}

    @Test
    void testOnStart() {
        // simulate start
        mockListener.onStart();
        verify(mockListener).onStart();       // verify invocation :contentReference[oaicite:1]{index=1}
    }

    @Test
    void testOnData() {
        String chunk = "partial text";
        mockListener.onData(chunk);
        verify(mockListener).onData(chunk);   // verify data chunk handling :contentReference[oaicite:2]{index=2}
    }

    @Test
    void testOnTokenReceived() {
        String token = "token";
        mockListener.onTokenReceived(token);
        verify(mockListener).onTokenReceived(token); // verify token callback :contentReference[oaicite:3]{index=3}
    }

    @Test
    void testOnEnd() {
        mockListener.onEnd();
        verify(mockListener).onEnd();         // verify end of stream :contentReference[oaicite:4]{index=4}
    }

    @Test
    void testOnError() {
        Exception ex = new RuntimeException("fail");
        mockListener.onError(ex);
        verify(mockListener).onError(ex);     // verify error handling :contentReference[oaicite:5]{index=5}
    }

    @Test
    void testOnStreamComplete() {
        mockListener.onStreamComplete();
        verify(mockListener).onStreamComplete();     // verify completion callback :contentReference[oaicite:6]{index=6}
    }

//    @Test
//    void testOnStreamCompleted_withResponseAndError() {
//        PromptResponse resp = new PromptResponse.Builder().text("final").usage(new TokenUsage(100, 100, 200)).build();
//        Exception err = null;
//        mockListener.onStreamCompleted(resp, err);
//        verify(mockListener).onStreamCompleted(resp, err); // verify final combined callback :contentReference[oaicite:7]{index=7}
//    }
}


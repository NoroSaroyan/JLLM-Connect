package model;

import com.jllm.core.model.Message;
import com.jllm.core.model.PromptRequest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PromptRequestTest {

    @Test
    void testBuilderWithAllFields() {
        PromptRequest request = PromptRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(new Message("user", "Hello")))
                .temperature(0.7)
                .maxTokens(100)
                .user("test-user")
                .build();

        assertNotNull(request);
        assertEquals("gpt-3.5-turbo", request.getModel());
        assertEquals(1, request.getMessages().size());
        assertEquals("Hello", request.getMessages().get(0).getContent());
    }

    @Test
    void testBuilderWithRequiredFieldsOnly() {
        PromptRequest request = PromptRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(new Message("user", "Hello")))
                .build();

        assertNotNull(request);
        assertEquals("gpt-3.5-turbo", request.getModel());
        assertEquals(1, request.getMessages().size());
        assertEquals("Hello", request.getMessages().get(0).getContent());
        assertEquals("user", request.getMessages().get(0).getRole());
    }

    @Test
    void testBuilderMissingModelThrowsException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            PromptRequest.builder()
                    .messages(List.of(new Message("user", "Hello")))
                    .build();
        });
        assertEquals("model cannot be null", exception.getMessage());
    }

    @Test
    void testBuilderMissingMessagesThrowsException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            PromptRequest.builder()
                    .model("gpt-3.5-turbo")
                    .build();
        });

        assertEquals("messages cannot be null or empty", exception.getMessage());
    }
}

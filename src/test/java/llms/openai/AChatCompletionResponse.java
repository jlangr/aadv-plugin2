package llms.openai;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AChatCompletionResponse {
    @Test
    void answersContentOfFirstMessage() {
        var choices = List.of(new Choice(0, new Message("", "my content"), null, ""));
        var response = new ChatCompletionResponse("", null, 0, null, choices, null, "");
        
        var result = response.firstMessageContent();
        
        assertEquals("my content", result);
    }
}
package llms;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static llms.OpenAIClient.MESSAGE_TYPE_SYSTEM;
import static llms.OpenAIClient.PROMPT_ASSISTANT_GUIDELINES;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnOpenAIClient {
    @Test
    void usesARequestBodyWithAllNecessaryMessages() {
        var client = new OpenAIClient();
        List<Example> examples = new ArrayList<>();

        var messages = client.createRequestMessages("my prompt", examples);

        assertEquals(new Message(MESSAGE_TYPE_SYSTEM, PROMPT_ASSISTANT_GUIDELINES), messages[0]);
//        assetEquals()
    }
}
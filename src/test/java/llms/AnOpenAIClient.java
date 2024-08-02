package llms;

import org.junit.jupiter.api.Test;
import utils.Http;

import static llms.OpenAIClient.MESSAGE_TYPE_SYSTEM;
import static llms.OpenAIClient.PROMPT_ASSISTANT_GUIDELINES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class AnOpenAIClient {
    OpenAIClient client = new OpenAIClient();

    @Test
    void usesARequestBodyWithAllNecessaryMessages() {
        var examples = new ExampleList();

        var messages = client.createRequestMessages("my prompt", examples);

        assertEquals(new Message(MESSAGE_TYPE_SYSTEM, PROMPT_ASSISTANT_GUIDELINES), messages[0]);
    }

    // TODO
//    @Test
//    void retrieveCompletion() {
//        var http = mock(Http.class);
//
//        client.retrieveCompletion("prompt", )
//
//    }
}
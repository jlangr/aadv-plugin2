package llms;

import llms.openai.Message;
import llms.openai.OpenAIClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plugin.settings.AADVPluginSettings;
import utils.Http;

import java.net.http.HttpRequest;

import static llms.openai.OpenAIClient.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class AnOpenAIClient {
    @Mock
    Http http;

    @Mock
    HttpRequest request;

    @Mock
    AADVPluginSettings aadvPluginSettings;

    @InjectMocks
    OpenAIClient client;

    @Test
    void usesARequestBodyWithAllNecessaryMessages() {
        var examples = new ExampleList();

        var messages = client.createRequestMessages("my prompt", examples);

        assertEquals(new Message(MESSAGE_TYPE_SYSTEM, PROMPT_ASSISTANT_GUIDELINES), messages[0]);
    }

//    @Test
//    void retrieveCompletion() {
//        var contentWithTestFile = "/* test class A.java */\nclass A{}\n/* end test class */";
//        var examples = new ExampleList(new Example("1", "name", "text"));
//        var choice = new Choice(1, new Message("", contentWithTestFile), "", null);
//        var response = new ChatCompletionResponse(
//           "1", "", 1L, "model",
//           List.of(choice), null, "fingerprint");
//        var requestBody = client.createRequestBody("prompt", examples);
//        when(aadvPluginSettings.retrieveAPIKey()).thenReturn("apikey");
//        when(http.createPostRequest(any(HashMap.class), eq("apiKey"), eq(API_URL)))
//           .thenReturn(request);
//        when(http.send(request)).thenReturn(response);
//
//        var files = client.retrieveCompletion("prompt", examples);
//
//        assertEquals(new SourceFile(FileType.TEST, "class A{}", "A.java"),
//           files.testFiles().get(0));
//    }
}
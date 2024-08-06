package llms.openai;

import llms.*;
import plugin.settings.AADVPluginSettings;
import utils.Http;

import java.util.HashMap;

public class OpenAIClient {
   static final String MESSAGE_ROLE_USER = "user";
   public static final String MESSAGE_TYPE_SYSTEM = "system";
   static final String API_URL = "https://api.openai.com/v1/chat/completions";

   private final Http http;
   private final AADVPluginSettings aadvPluginSettings;
   private Prompt prompt;

   public OpenAIClient(Http http, AADVPluginSettings aadvPluginSettings) {
      this.http = http;
      this.aadvPluginSettings = aadvPluginSettings;
   }

   public Files retrieveCompletion(Prompt prompt) {
      var apiKey = aadvPluginSettings.retrieveAPIKey();
      var requestBody = createRequestBody(toOpenAIMessages(prompt));
      var request = http.createPostRequest(requestBody, apiKey, API_URL);
      var completion = (ChatCompletionResponse)http.send(request);
      return new CodeResponseSplitter().split(completion.firstMessageContent());
   }

   private Message[] toOpenAIMessages(Prompt prompt) {
      return prompt.messages().stream()
         .map(promptMessage ->
            new Message(role(promptMessage.promptMessageType()), promptMessage.text()))
         .toArray(Message[]::new);
   }

   private String role(PromptMessageType promptMessageType) {
      return switch(promptMessageType) {
         case system -> "system";
         case user -> "user";
      };
   }

   private HashMap<Object, Object> createRequestBody(Message[] messages) {
      var requestBody = new HashMap<>();
      requestBody.put("model", "gpt-4o");
      requestBody.put("messages", messages);
      requestBody.put("max_tokens", 4096);
      return requestBody;
   }
}

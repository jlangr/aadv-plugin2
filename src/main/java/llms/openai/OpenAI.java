package llms.openai;

import llms.*;
import java.util.HashMap;

public class OpenAI {
   static final String MESSAGE_ROLE_USER = "user";
   public static final String MESSAGE_TYPE_SYSTEM = "system";

   private final OpenAIClient client;

   public OpenAI(OpenAIClient client) {
      this.client = client;
   }

   public Files retrieveCompletion(Prompt prompt) {
      var requestBody = createRequestBody(toOpenAIMessages(prompt));
      var completion = client.retrieveCompletion(requestBody);
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
         case SYSTEM -> MESSAGE_TYPE_SYSTEM;
         case USER -> MESSAGE_ROLE_USER;
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

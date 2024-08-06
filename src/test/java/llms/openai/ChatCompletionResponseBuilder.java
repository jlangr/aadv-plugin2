package llms.openai;

import java.util.ArrayList;
import java.util.List;

public class ChatCompletionResponseBuilder {
   private String id = "defaultId";
   private String object = "defaultObject";
   private long created = System.currentTimeMillis();
   private String model = "defaultModel";
   private List<Choice> choices = new ArrayList<>();
   private Usage usage = new Usage(1, 1, 2);
   private String systemFingerprint = "defaultFingerprint";

   public ChatCompletionResponseBuilder addChoice(String messageContent) {
      Message message = new Message("defaultRole", messageContent);
      Choice choice = new Choice(choices.size(), message, null, "defaultFinishReason");
      choices.add(choice);
      return this;
   }

   public ChatCompletionResponse build() {
      return new ChatCompletionResponse(id, object, created, model, choices, usage, systemFingerprint);
   }
}

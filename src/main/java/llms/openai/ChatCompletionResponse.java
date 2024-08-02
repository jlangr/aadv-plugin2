package llms.openai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChatCompletionResponse(
   String id, String object, long created, String model, List<Choice>choices, Usage usage, String systemFingerprint) {

   public String firstMessageContent() {
      return choices().get(0).message().content();
   }
}

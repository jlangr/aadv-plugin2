package llms.openai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Choice(
   int index,
   Message message,
   Object logprobs,
   String finishReason
) {}

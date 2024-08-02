package llms.openai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record Usage(int promptTokens, int completionTokens, int totalTokens) {}

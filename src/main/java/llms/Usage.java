package llms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record Usage(int promptTokens, int completionTokens, int totalTokens) {}

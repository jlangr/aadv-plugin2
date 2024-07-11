package llms;

import plugin.AADVPluginSettings;
import utils.Http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public class OpenAIClient {
   static final String MESSAGE_ROLE_USER = "user";
   static final String MESSAGE_TYPE_SYSTEM = "system";
   static final String API_URL = "https://api.openai.com/v1/chat/completions";
   static final String PROMPT_OVERVIEW = "Generate JUnit test class(es) and production Java code for the solution. " +
      "In output, begin each code listing with a header in either the form:\n/* test class TestFileName.java */\nor:\n/* prod class ProdFileName.java */\nEnd each code listing with a footer, either:\n/* end test class */\nor:\n/* end prod class */. Substitute the real file name for TestFileName and ProdFileName. ";
   static final String PROMPT_ASSISTANT_GUIDELINES = "You're a Java programming assistant. When asked to generate solution code, include only code. Don't include any explanation. Don't include comments in any code.";
   static final String PROMPT_TEXT = "Generate code for this:";
   static final String PROMPT_EXAMPLES = "Examples:";

   Http http = new Http();

   public Files retrieveCompletion(String prompt, List<Example> examples) {
      var apiKey = new AADVPluginSettings().retrieveAPIKey();
      var requestBody = createRequestBody(prompt, examples);
      var request = http.createPostRequest(requestBody, apiKey, API_URL);
      var completion = (ChatCompletionResponse)http.send(request);
      return new CodeResponseSplitter().split(completion.firstMessageContent());
   }

   private HashMap<Object, Object> createRequestBody(String prompt, List<Example> examples) {
      var requestBody = new HashMap<>();
      requestBody.put("model", "gpt-4o");
      requestBody.put("messages", createRequestMessages(prompt, examples));
      requestBody.put("max_tokens", 4096);
      return requestBody;
   }

   Message[] createRequestMessages(String prompt, List<Example> examples) {
      var messages = new ArrayList<Message>();
      messages.add(new Message(MESSAGE_TYPE_SYSTEM, PROMPT_ASSISTANT_GUIDELINES));
      messages.add(new Message(MESSAGE_ROLE_USER, generatePrompt(prompt, examples)));
      return messages.toArray(new Message[0]);
   }

   private String generatePrompt(String prompt, List<Example> examples) {
       return getString(prompt, concatenateExamples(examples));
   }

   private String getString(String prompt, String examplesText) {
      var builder = new StringBuilder();
      builder.append(format("%n%s%n", PROMPT_OVERVIEW));
      builder.append(format("%n%s%n", PROMPT_TEXT));
      builder.append(prompt);
      builder.append(format("%n%s%n", PROMPT_EXAMPLES));
      builder.append(format("%n%s%n", examplesText));
      return builder.toString();
   }

   String concatenateExamples(List<Example> examples) {
       return examples.stream()
          .map(Example::getText)
          .collect(joining("\n---\n"));
   }
}

package llms;

import plugin.AADVPluginSettings;
import utils.Http;
import java.util.HashMap;

public class OpenAIClient {
   static final String API_URL = "https://api.openai.com/v1/chat/completions";

   Http http = new Http();

   record Message(String role, String content) {}

   public Files retrieveCompletion(String prompt) {
      var apiKey = new AADVPluginSettings().retrieveAPIKey();
      var requestBody = createRequestBody(prompt);
      var request = http.createPostRequest(requestBody, apiKey, API_URL);
      var completion = (ChatCompletionResponse)http.send(request);
      return new CodeResponseSplitter().split(completion.firstMessageContent());
   }

   private HashMap<Object, Object> createRequestBody(String prompt) {
      var requestBody = new HashMap<>();
      requestBody.put("model", "gpt-4o");
      requestBody.put("messages", new Message[] {
         new Message("system", "You're a Java programming assistant. When asked to generate solution code, include only code. Don't include any explanation. Don't include comments in any code."),
         new Message("user", "Generate JUnit test class(es) and production Java code for the solution. " +
         "In output, begin each code listing with a header in either the form:\n/* test class TestFileName.java */\nor:\n/* prod class ProdFileName.java */\nEnd each code listing with a footer, either:\n/* end test class */\nor:\n/* end prod class */. Substitute the real file name for TestFileName and ProdFileName. Generate code for this:" +
            prompt),
      });
      requestBody.put("max_tokens", 4096);
      return requestBody;
   }
}

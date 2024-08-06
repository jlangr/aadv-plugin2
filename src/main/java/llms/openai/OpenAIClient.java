package llms.openai;

import plugin.settings.AADVPluginSettings;
import utils.Http;
import java.util.HashMap;

public class OpenAIClient {
   public static final String API_URL = "https://api.openai.com/v1/chat/completions";
   private final Http http;
   private final AADVPluginSettings aadvPluginSettings;

   public OpenAIClient(Http http, AADVPluginSettings aadvPluginSettings) {
      this.http = http;
      this.aadvPluginSettings = aadvPluginSettings;
   }

   public ChatCompletionResponse retrieveCompletion(HashMap<Object, Object> requestBody) {
      var apiKey = aadvPluginSettings.retrieveAPIKey();
      var request = http.createPostRequest(requestBody, apiKey, API_URL);
      return (ChatCompletionResponse)http.send(request);
   }
}

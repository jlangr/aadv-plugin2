package plugin;

// TODO test
public class AADVPluginSettings {
   public String retrieveAPIKey() {
      OpenAISettingsState settingsState = OpenAISettingsState.getInstance();
      if (settingsState == null || settingsState.getApiKey() == null || settingsState.getApiKey().isEmpty()) {
         return null;
      }

      return settingsState.getApiKey();
   }
}

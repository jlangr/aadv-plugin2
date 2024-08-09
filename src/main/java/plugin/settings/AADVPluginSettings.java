package plugin.settings;

public class AADVPluginSettings {
   public String retrieveAPIKey() {
      var settingsState = AADVSettingsState.getInstance();
      if (settingsState == null || settingsState.getApiKey() == null || settingsState.getApiKey().trim().isEmpty())
         return null;

      return settingsState.getApiKey().trim();
   }
}

package plugin.settings;

public class AADVPluginSettings {
   public String retrieveAPIKey() {
      var settingsState = AADVSettingsState.getInstance();
      if (settingsState == null || settingsState.getApiKey() == null)
         return null;

      return settingsState.getApiKey().trim();
   }
}

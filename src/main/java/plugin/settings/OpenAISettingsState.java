package plugin.settings;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;

@State(
   name = "com.example.OpenAISettingsState",
   storages = @Storage("OpenAISettings.xml")
)
@Service(Service.Level.APP)
public final class OpenAISettingsState implements PersistentStateComponent<OpenAISettingsState> {
   static OpenAISettingsState DEFAULT_INSTANCE =
      ApplicationManager.getApplication().getService(OpenAISettingsState.class);
   private static OpenAISettingsState instance = DEFAULT_INSTANCE;

   private String apiKey;
   private StyleSettings styleSettings;

   public static void setInstance(OpenAISettingsState openAISettingsState) {
      instance = openAISettingsState;
   }

   public static void reset() {
      instance = DEFAULT_INSTANCE;
   }

   @Override
   public OpenAISettingsState getState() {
      return this;
   }

   @Override
   public void loadState(OpenAISettingsState state) {
      this.apiKey = state.apiKey;
   }

   public static OpenAISettingsState getInstance() {
      return instance;
   }

   public String getApiKey() {
      return apiKey;
   }

   public void setApiKey(String apiKey) {
      this.apiKey = apiKey;
   }

   public StyleSettings getStyleSettings() {
      return styleSettings;
   }

   public void setStyleSettings(StyleSettings styleSettings) {
      this.styleSettings = styleSettings;
   }
}

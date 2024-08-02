package plugin.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.Nullable;

@State(
   name = "com.example.OpenAISettingsState",
   storages = @Storage("OpenAISettings.xml")
)
@Service(Service.Level.APP)
public final class OpenAISettingsState implements PersistentStateComponent<OpenAISettingsState> {
   private String apiKey;

   @Nullable
   @Override
   public OpenAISettingsState getState() {
      return this;
   }

   @Override
   public void loadState(OpenAISettingsState state) {
      this.apiKey = state.apiKey;
   }

   public static OpenAISettingsState getInstance() {
      return ApplicationManager.getApplication().getService(OpenAISettingsState.class);
   }

   public String getApiKey() {
      return apiKey;
   }

   public void setApiKey(String apiKey) {
      this.apiKey = apiKey;
   }
}

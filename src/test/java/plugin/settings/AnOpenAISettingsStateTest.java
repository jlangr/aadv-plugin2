package plugin.settings;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnOpenAISettingsStateTest {
   private Application application;

   @BeforeEach
   void injectSettingsStateIntoApplication() {
      application = mock(Application.class);
      ApplicationManager.setApplication(application);
   }

   @Test
   void returnsApiKey() {
      var settingsState = new OpenAISettingsState();
      settingsState.setApiKey("123");
      when(application.getService(OpenAISettingsState.class)).thenReturn(settingsState);
      OpenAISettingsState.setInstance(settingsState);

      var key = OpenAISettingsState.getInstance().getApiKey();

      assertEquals("123", key);
   }

   @Test
   void loadsFromSettingsState() {
      var settingsState = new SettingsStateBuilder().withApiKey("999").build();
      when(application.getService(OpenAISettingsState.class)).thenReturn(settingsState);
      OpenAISettingsState.setInstance(settingsState);
      var state = OpenAISettingsState.getInstance();

      var newSettingsState = new SettingsStateBuilder().withApiKey("123").build();
      OpenAISettingsState.getInstance().loadState(newSettingsState);

      assertEquals("123", state.getApiKey());
   }

   @Test
   void returnsSelfFromGetState() {
      var settingsState = new SettingsStateBuilder().build();
      when(application.getService(OpenAISettingsState.class)).thenReturn(settingsState);
      OpenAISettingsState.setInstance(settingsState);
      var state = OpenAISettingsState.getInstance();

      var result = state.getState();

      assertSame(settingsState, result);
   }

   class SettingsStateBuilder {
      private String apiKey;

      SettingsStateBuilder withApiKey(String apiKey) {
         this.apiKey = apiKey;
         return this;
      }

      OpenAISettingsState build() {
         var state = new OpenAISettingsState();
         state.setApiKey(apiKey);
         return state;
      }
   }
}
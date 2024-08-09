package plugin.settings;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnAADVSettingsComponentStateTest {
   private Application application;


   @BeforeEach
   void injectSettingsStateIntoApplication() {
      application = mock(Application.class);
      ApplicationManager.setApplication(application);
   }

//   @Test
//   void returnsApiKey() {
//      var settingsState = new AADVSettingsState();
//      settingsState.setApiKey("123");
//      when(application.getService(AADVSettingsState.class)).thenReturn(settingsState);
//      AADVSettingsState.setInstance(settingsState);
//
//      var key = AADVSettingsState.getInstance().getApiKey();
//
//      assertEquals("123", key);
//   }
//
//   @Test
//   void loadsFromSettingsState() {
//      var settingsState = new SettingsStateBuilder().withApiKey("999").build();
//      when(application.getService(AADVSettingsState.class)).thenReturn(settingsState);
//      AADVSettingsState.setInstance(settingsState);
//      var state = AADVSettingsState.getInstance();
//
//      var newSettingsState = new SettingsStateBuilder().withApiKey("123").build();
//      AADVSettingsState.getInstance().loadState(newSettingsState);
//
//      assertEquals("123", state.getApiKey());
//   }
//
//   @Test
//   void returnsSelfFromGetState() {
//      var settingsState = new SettingsStateBuilder().build();
//      when(application.getService(AADVSettingsState.class)).thenReturn(settingsState);
//      AADVSettingsState.setInstance(settingsState);
//      var state = AADVSettingsState.getInstance();
//
//      var result = state.getState();
//
//      assertSame(settingsState, result);
//   }
//
//   class SettingsStateBuilder {
//      private String apiKey;
//
//      SettingsStateBuilder withApiKey(String apiKey) {
//         this.apiKey = apiKey;
//         return this;
//      }
//
//      AADVSettingsState build() {
//         var state = new AADVSettingsState();
//         state.setApiKey(apiKey);
//         return state;
//      }
//   }
}
package plugin.settings;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnAADVPluginSettings {
   @Nested
   class RetrieveApiKey {
      Application application;
      AADVPluginSettings pluginSettings = new AADVPluginSettings();

      @BeforeEach
      void setUp() {
         application = mock(Application.class);
         ApplicationManager.setApplication(application);
      }

      @Test
      void returnsNullWhenSettingsStateNull() {
         when(application.getService(AADVSettingsState.class)).thenReturn(null);

         assertEquals(null, pluginSettings.retrieveAPIKey());
      }

      @Nested
      class WithNonNullSettingsState {
         AADVSettingsState settingsState;

         @BeforeEach
         void setup() {
            settingsState = new AADVSettingsState();
            when(application.getService(AADVSettingsState.class)).thenReturn(settingsState);
         }

         @Test
         void returnsNullWhenApiKeyIsNull() {
            settingsState.setApiKey(null);

            var result = pluginSettings.retrieveAPIKey();

            assertEquals(null, result);
         }

         @Test
         void returnsEmptyWhenApiKeyIsEmpty() {
            settingsState.setApiKey("  \n \t \r ");

            var result = pluginSettings.retrieveAPIKey();

            assertEquals("", result);
         }

         @Test
         void returnsTrimmedApiKeyWhenNotEmpty() {
            settingsState.setApiKey(" my api key ");

            var result = pluginSettings.retrieveAPIKey();

            assertEquals("my api key", result);
         }
      }
   }

   @AfterEach
   void cleanup() {
//      AADVSettingsState.reset();
   }
}
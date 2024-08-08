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
      static Application application;
      AADVPluginSettings pluginSettings = new AADVPluginSettings();

      @BeforeAll
      static void setUp() {
         application = mock(Application.class);
         ApplicationManager.setApplication(application);
      }

      @Test
      void returnsNullWhenSettingsStateNull() {
         when(application.getService(OpenAISettingsState.class)).thenReturn(null);

         assertEquals(null, pluginSettings.retrieveAPIKey());
      }

      @Nested
      class WithNonNullSettingsState {
         OpenAISettingsState settingsState = new OpenAISettingsState();

         @BeforeEach
         void setup() {
            OpenAISettingsState.setInstance(settingsState);
         }

         @Test
         void returnsNullWhenApiKeyIsNull() {
            settingsState.setApiKey(null);

            var result = pluginSettings.retrieveAPIKey();

            assertEquals(null, result);
         }

         @Test
         void returnsNullWhenApiKeyIsEmpty() {
            settingsState.setApiKey("  \n \t \r ");

            var result = pluginSettings.retrieveAPIKey();

            assertEquals(null, result);
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
      OpenAISettingsState.reset();
   }
}
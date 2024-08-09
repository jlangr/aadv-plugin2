package plugin.settings;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnAADVSettingsState {
   Application application;

   @BeforeEach
   void setApplication() {
      application = mock(Application.class);
      ApplicationManager.setApplication(application);
   }

   @Test
   void getInstanceReturnsStoredSettingsState() {
      storeOnApplication(new SettingsStateBuilder()
         .withApiKey("123")
         .withLanguage("Java", "Rule 1", "Rule 2")
         .build());

      var state = AADVSettingsState.getInstance();

      assertEquals("123", state.getApiKey());
      var languages = state.getStyleSettings().languages();
      var java = languages.get(0);
      assertEquals("Java", java.getName());
      assertEquals("Rule 1", java.getRules().get(0).getText());
      assertEquals("Rule 2", java.getRules().get(1).getText());
   }

   private void storeOnApplication(AADVSettingsState storedSettingsState) {
      when(application.getService(AADVSettingsState.class)).thenReturn(storedSettingsState);
   }

   //   @Test
//   void loadsFromSettingsState() {
//      var settingsState = new SettingsStateBuilder().withApiKey("999").build();
//      when(application.getService(AADVSettingsState.class)).thenReturn(settingsState);
//      var state = AADVSettingsState.getInstance();
//
//      var newSettingsState = new SettingsStateBuilder().withApiKey("123").build();
//      AADVSettingsState.getInstance().loadState(newSettingsState);
//
//      assertEquals("123", state.getApiKey());
//   }

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
   class SettingsStateBuilder {
      private String apiKey;
      private List<Language> languages = new ArrayList<>();

      SettingsStateBuilder withApiKey(String apiKey) {
         this.apiKey = apiKey;
         return this;
      }

      public SettingsStateBuilder withLanguage(String languageName, String... ruleTexts) {
         var rules = stream(ruleTexts).map(s -> new Rule(s, true)).toList();
         var language = new Language(languageName, rules);
         languages.add(language);
         return this;
      }

      AADVSettingsState build() {
         var state = new AADVSettingsState();
         state.setApiKey(apiKey);
         state.setStyleSettings(new StyleSettings(languages));
         return state;
      }
   }
}
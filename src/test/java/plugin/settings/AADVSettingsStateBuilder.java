package plugin.settings;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

//   @Test
//   void returnsSelfFromGetState() {
//      var settingsState = new AADVSettingsStateBuilder().build();
//      when(application.getService(AADVSettingsState.class)).thenReturn(settingsState);
//      AADVSettingsState.setInstance(settingsState);
//      var state = AADVSettingsState.getInstance();
//
//      var result = state.getState();
//
//      assertSame(settingsState, result);
//   }
//
class AADVSettingsStateBuilder {
   private String apiKey;
   private List<Language> languages = new ArrayList<>();

   AADVSettingsStateBuilder withApiKey(String apiKey) {
      this.apiKey = apiKey;
      return this;
   }

   public AADVSettingsStateBuilder withLanguage(String languageName, String... ruleTexts) {
      var rules = stream(ruleTexts).map(s -> new Rule(s)).toList();
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

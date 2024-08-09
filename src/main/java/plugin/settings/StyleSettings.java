package plugin.settings;

import java.util.ArrayList;
import java.util.List;

// TODO test
public record StyleSettings(List<Language> languages) {
   public StyleSettings clone() {
      var clonedLanguages = new ArrayList<Language>();
      for (var language : languages()) {
         var clonedRules = new ArrayList<>(language.getRules());
         clonedLanguages.add(new Language(language.getName(), clonedRules));
      }
      return new StyleSettings(clonedLanguages);
   }
}

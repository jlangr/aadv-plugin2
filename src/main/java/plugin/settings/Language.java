package plugin.settings;

import java.util.ArrayList;
import java.util.List;

public class Language {
   private String name;
   private List<Rule> rules = new ArrayList<>();

   // Public no-arg constructor needed for deserialization
   public Language() {
   }

   public Language(String name, List<Rule> rules) {
      this.name = name;
      this.rules = rules;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<Rule> getRules() {
      return rules;
   }

   public void setRules(List<Rule> rules) {
      this.rules = rules;
   }

   @Override
   public String toString() {
      return "Language{" +
         "name='" + name + '\'' +
         ", rules=" + rules +
         '}';
   }
}


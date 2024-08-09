package plugin.settings;

public class Rule {
   private String text;
   private boolean enabled;

   // Public no-arg constructor needed for deserialization
   public Rule() {
   }

   public Rule(String text, boolean enabled) {
      this.text = text;
      this.enabled = enabled;
   }

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public boolean isEnabled() {
      return enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   @Override
   public String toString() {
      return "Rule{" +
         "text='" + text + '\'' +
         ", enabled=" + enabled +
         '}';
   }
}

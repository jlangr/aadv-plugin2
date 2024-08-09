package plugin.settings;

import java.util.UUID;

public class Rule {
   private String id ;
   private String text;
   private boolean enabled = true;

   public Rule() { // needed for deserialization
   }

   public Rule(String text) {
      this.text = text;
      this.id = UUID.randomUUID().toString();
   }

   public String getId() {
      return id;
   }

   // TODO can this be deleted
   public void setId(String id) {
      this.id = id;
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

   public void toggleEnabled() {
      this.enabled = !enabled;
   }

   @Override
   public String toString() {
      return "Rule{" +
         "id='" + id + '\'' +
         "text='" + text + '\'' +
         ", enabled=" + enabled +
         '}';
   }
}

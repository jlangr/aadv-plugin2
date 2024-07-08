package plugin;

import java.util.Objects;

public class Example {
   private String text;
   private final String id;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Example example = (Example) o;
      return Objects.equals(id, example.id);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(id);
   }

   public Example(String id, String text) {
      this.text = text;
      this.id = id;
   }

   public String getText() {
      return text;
   }

   public String getId() {
      return id;
   }

   public void setText(String text) {
      this.text = text;
   }
}

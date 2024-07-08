package plugin;

import java.util.Objects;

class Example {
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

   Example(String id, String text) {
      this.text = text;
      this.id = id;
   }

   String getText() {
      return text;
   }

   String getId() {
      return id;
   }

   void setText(String text) {
      this.text = text;
   }
}

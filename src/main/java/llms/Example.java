package llms;

import java.util.Objects;

public class Example {
   public static final Example EMPTY = new Example("", "", "");

   private String name;
   private String text;
   private ExampleState state;
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

   @Override
   public String toString() {
      return "[Example; id=" + id + ", text=" + text + "]";
   }

   public Example(String id, String text, String name) {
      this.text = text;
      this.id = id;
      this.name = name;
   }

   public Example(String id, String text) {
      this(id, text, "");
   }

   public String getId() {
      return id;
   }

   public void setText(String text) {
      this.text = text;
   }

   public String getText() {
      return text;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   String toPromptString() {
      var builder = new StringBuilder();
      if (name != null && !name.isEmpty()) builder.append("Example: " + name + "\n");
      builder.append(text);
      return builder.toString();
   }
}

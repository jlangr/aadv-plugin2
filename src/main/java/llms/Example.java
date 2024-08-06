package llms;

public record Example(String id, String name, String text, boolean isEnabled) {
   public static final Example EMPTY = new Example("", "", "");

   public Example(String id, String name, String text) {
      this(id, name, text, true);
   }

   String toPromptText() {
      var builder = new StringBuilder();
      if (name != null && !name.isEmpty()) builder.append("name: ").append(name).append("\n");
      builder.append(text);
      return builder.toString();
   }
}

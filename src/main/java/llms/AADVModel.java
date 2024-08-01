package llms;

import ui.SourcePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AADVModel {
   public static final String MSG_EXAMPLES = "Examples:";
   private List<SourcePanel> panels = new ArrayList<>();
   private ExampleList exampleList = new ExampleList();
   private String promptText = "";

   public void add(SourcePanel panel) {
      panels.add(panel);
   }

   public Optional<SourcePanel> getPanel(SourceFile file) {
      return panels.stream()
         .filter(panel -> panel.getName().equals(file.fileName()))
         .findFirst();
   }

   public void remove(SourcePanel panel) {
      panels.remove(panel);
   }

   // Examples
   public void upsertExample(String id, String name, String text) {
      exampleList.update(id, name, text);
   }

   public void addExample(String id) {
      exampleList.add(id, "", "");
   }

   public ExampleList getExampleList() {
      return exampleList;
   }

   public List<Example> getExamples() {
      return exampleList.getAll();
   }

   public Example getExample(String id) {
      return exampleList.get(id);
   }

   public void deleteExample(String id) {
      exampleList.deleteExample(id);
   }

   public void toggleEnabled(String id) {
      exampleList.toggleEnabled(id);
   }

// ===

   public String combinedPrompt() {
      var stringBuilder = new StringBuilder();
      stringBuilder.append(promptText);
      stringBuilder.append("\n");
      stringBuilder.append(MSG_EXAMPLES);
      stringBuilder.append("\n");
      stringBuilder.append(exampleList.concatenate());
      return stringBuilder.toString();
   }

   public void setPromptText(String text) {
      promptText = text;
   }
}

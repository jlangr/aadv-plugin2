package llms;

import ui.SourcePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AADVModel {
   private final List<SourcePanel> panels = new ArrayList<>();
   private final ExampleList exampleList = new ExampleList();
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
   public void updateExample(String id, String name, String text) {
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
      exampleList.delete(id);
   }

   public void toggleEnabled(String id) {
      exampleList.toggleEnabled(id);
   }

// ===

   public String dumpPrompt() {
      var prompt = new Prompt(promptText, exampleList);
      return prompt.messages().toString();
   }

   public void setPromptText(String text) {
      promptText = text;
   }

   public String getPromptText() {
      return promptText;
   }
}

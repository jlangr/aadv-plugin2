package llms;

import ui.SourcePanel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AADVModel {
   private List<SourcePanel> panels = new ArrayList<>();
   private ExampleList exampleList = new ExampleList();

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

   public void updateExampleText(String id, String text) {
      exampleList.update(id, text);
   }

   public void addExample(String id, String name, String text) {
      exampleList.add(id, name, text);
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
}

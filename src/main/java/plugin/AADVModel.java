package plugin;

import llms.Example;
import llms.ExampleNotFoundException;
import llms.SourceFile;
import ui.SourcePanel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AADVModel {
   private List<SourcePanel> panels = new ArrayList<>();
   private List<Example> examples = new ArrayList<>();

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

   public void updateExample(String id, String text) {
      var example = getExample(id);
      example.setText(text);
   }

   public void addExample(String id, String text) {
      examples.add(new Example(id, text));
   }

   public List<Example> getExamples() {
      return examples;
   }

   public String getExampleText(String id) {
      return getExample(id).getText();
   }

   public Example getExample(String id) {
      var result = examples.stream()
         .filter(e -> e.getId().equals(id))
         .findFirst();
      if (result.isEmpty())
         throw new ExampleNotFoundException();
      return result.get();
   }

   public void deleteExample(String id) {
      var iterator = examples.iterator();
      while (iterator.hasNext()) {
         var example = iterator.next();
         if (example.getId().equals(id)) {
            iterator.remove();
            break;
         }
      }
   }
}

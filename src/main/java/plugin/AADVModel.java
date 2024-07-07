package plugin;

import llms.SourceFile;
import ui.SourcePanel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AADVModel {
   private List<SourcePanel> panels = new ArrayList<>();

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
}

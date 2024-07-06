package plugin;

import com.intellij.openapi.project.Project;
import ui.AADVPanel;

import javax.swing.*;

public class AADVController {
   private final Project project;
   private final JComponent view;

   public AADVController(Project project) {
      this.project = project;
      var apiKey = new AADVPluginSettings().retrieveAPIKey();
      this.view = new AADVPanel(this.project, apiKey);
   }

   public JComponent getComponent() {
      return view;
   }
}

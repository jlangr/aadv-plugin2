package plugin;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import ui.AADVPanel;

public class AADVToolWindowFactory implements ToolWindowFactory, DumbAware {
   @Override
   public void createToolWindowContent(Project project, ToolWindow toolWindow) {
      var apiKey = new AADVPluginSettings().retrieveAPIKey();

      var contentFactory = ContentFactory.getInstance();
      var content = contentFactory.createContent(new AADVPanel(project, apiKey), "AADV Helper", false);
      toolWindow.getContentManager().addContent(content);
   }
}


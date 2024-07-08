package plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;

public class AADVOutputWindowFactory implements ToolWindowFactory {
   @Override
   public void createToolWindowContent(Project project, ToolWindow toolWindow) {
      var controller = new AADVController(project);

      var contentFactory = ContentFactory.getInstance();
      var content = contentFactory.createContent(controller.getOutputView(), "", false);
      toolWindow.getContentManager().addContent(content);
   }
}

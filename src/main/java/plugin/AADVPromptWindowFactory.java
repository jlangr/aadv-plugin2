package plugin;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class AADVPromptWindowFactory implements ToolWindowFactory, DumbAware {
   @Override
   public void createToolWindowContent(@NotNull Project project, ToolWindow toolWindow) {
      var controller = AADVController.get(project);

      var contentFactory = ContentFactory.getInstance();
      var content = contentFactory.createContent(controller.promptView, "", false);
      toolWindow.getContentManager().addContent(content);
   }
}

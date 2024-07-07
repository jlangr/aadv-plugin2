package plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.ToolWindowManager;

public class AADVPluginAction extends AnAction {
   @Override
   public void actionPerformed(AnActionEvent e) {
      var project = e.getProject();
      if (project == null) return;

      var toolWindow = ToolWindowManager.getInstance(project).getToolWindow("AADV Helper");
      if (toolWindow != null) toolWindow.show(null);
   }
}

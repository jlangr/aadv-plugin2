package plugin;

import com.intellij.openapi.project.Project;
import llms.OpenAIClient;
import ui.AADVPanel;
import ui.SendPromptListener;

import javax.swing.*;

public class AADVController implements SendPromptListener {
   private final Project project;
   private final AADVPanel view;

//   private OpenAIClient openAIClient = new StubOpenAIClient(); // TODO change to prod
   private OpenAIClient openAIClient = new OpenAIClient();

   public AADVController(Project project) {
      this.project = project;
      this.view = new AADVPanel(this.project, this);
   }

   public JComponent getComponent() {
      return view;
   }

   @Override
   public void send(String text) {
      var apiKey = new AADVPluginSettings().retrieveAPIKey();
      if (apiKey == null) {
         view.showMessage(AADVPanel.MSG_KEY_NOT_CONFIGURED);
         return;
      }

      var files = openAIClient.retrieveCompletion(text);
      view.addSourcePanels(files.prodFiles());
      view.addSourcePanels(files.testFiles());
   }
}

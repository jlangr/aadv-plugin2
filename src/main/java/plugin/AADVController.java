package plugin;

import com.intellij.openapi.project.Project;
import llms.Files;
import llms.OpenAIClient;
import llms.SourceFile;
import ui.AADVPanel;
import ui.SendPromptListener;
import ui.SourcePanel;
import ui.UpdateFileListener;
import utils.idea.IDEAEditor;

import javax.swing.*;

public class AADVController implements SendPromptListener, UpdateFileListener {
   private final Project project;
   private final AADVPanel view;

//   private OpenAIClient openAIClient = new StubOpenAIClient(); // TODO change to prod
   private OpenAIClient openAIClient = new OpenAIClient();
   private AADVModel model = new AADVModel();

   public AADVController(Project project) {
      this.project = project;
      this.view = new AADVPanel(this);
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

      updateSourcePanels(files);
   }

   private void updateSourcePanels(Files files) {
      files.prodFiles().stream()
            .forEach(file -> upsertSourcePanel(file));
      files.testFiles().stream()
         .forEach(file -> upsertSourcePanel(file));
   }

   @Override
   public void update(SourceFile sourceFile) {
      new IDEAEditor().replaceEditorContent(project, sourceFile);
   }

   private void upsertSourcePanel(SourceFile file) {
      var existingPanel = model.getPanel(file);
      if (existingPanel.isPresent())
         existingPanel.get().updateContent(file);
      else {
         var panel = new SourcePanel(file, this);
         model.add(panel);
         view.addSourcePanel(panel);
      }
   }
}

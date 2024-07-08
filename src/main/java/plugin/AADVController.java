package plugin;

import com.intellij.openapi.project.Project;
import llms.Files;
import llms.OpenAIClient;
import llms.SourceFile;
import llms.StubOpenAIClient;
import ui.AADVPanel;
import ui.SendPromptListener;
import ui.SourcePanel;
import ui.SourcePanelListener;
import utils.idea.IDEAEditor;
import javax.swing.*;

public class AADVController implements SendPromptListener, SourcePanelListener {
   private final Project project;
   private final AADVPanel view;

   private OpenAIClient openAIClient
      = new StubOpenAIClient(); // TODO change to prod
//      = new OpenAIClient();
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

   @Override
   public void delete(SourceFile sourceFile) {
      var panel = model.getPanel(sourceFile).get();
      view.removeSourcePanel(panel);
      model.remove(panel);
   }

   private void upsertSourcePanel(SourceFile sourceFile) {
      var existingPanel = model.getPanel(sourceFile);
      if (existingPanel.isPresent())
         existingPanel.get().updateContent(sourceFile);
      else {
         var panel = new SourcePanel(sourceFile, this);
         model.add(panel);
         view.addSourcePanel(panel);
      }
   }
}

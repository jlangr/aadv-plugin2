package plugin;

import com.intellij.openapi.project.Project;
import llms.Files;
import llms.OpenAIClient;
import llms.SourceFile;
import llms.StubOpenAIClient;
import ui.*;
import utils.idea.IDEAEditor;
import javax.swing.*;

public class AADVController implements SendPromptListener, SourcePanelListener, ExampleListener {
   private static AADVController controller = null;
   private final Project project;
   private final AADVPromptPanel promptView;
   private final AADVOutputPanel outputView;

   private OpenAIClient openAIClient
      = new StubOpenAIClient(); // TODO change to prod
//      = new OpenAIClient();
   private AADVModel model = new AADVModel();

   private AADVController(Project project) {
      this.project = project;
      this.promptView = new AADVPromptPanel(this, this);
      this.outputView = new AADVOutputPanel();
   }

   public static synchronized AADVController get(Project project) {
      if (controller == null)
         controller = new AADVController(project);
      return controller;
   }

   public JComponent getOutputView() {
      return outputView;
   }

   public JComponent getPromptView() {
      return promptView;
   }

   @Override
   public void send(String text) {
      var apiKey = new AADVPluginSettings().retrieveAPIKey();
      if (apiKey == null) {
         promptView.showMessage(AADVPromptPanel.MSG_KEY_NOT_CONFIGURED);
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

   // source panel listener methods:
   @Override
   public void update(SourceFile sourceFile) {
      new IDEAEditor().replaceEditorContent(project, sourceFile);
   }

   @Override
   public void delete(SourceFile sourceFile) {
      var panel = model.getPanel(sourceFile).get();
      outputView.removeSourcePanel(panel);
      model.remove(panel);
   }

   private void upsertSourcePanel(SourceFile sourceFile) {
      var existingPanel = model.getPanel(sourceFile);
      if (existingPanel.isPresent())
         existingPanel.get().updateContent(sourceFile);
      else {
         var panel = new SourcePanel(sourceFile, this);
         model.add(panel);
         outputView.addSourcePanel(panel);
      }
   }

   // example listener methods
   @Override
   public void add(String text, String panelName) {
      System.out.println("adding " + panelName + " from model");
      model.addExample(panelName, text);
      promptView.refreshExamples(model.getExamples());
   }

   @Override
   public void delete(String panelName) {
      model.deleteExample(panelName);
      promptView.refreshExamples(model.getExamples());
   }
}

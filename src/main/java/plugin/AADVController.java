package plugin;

import com.intellij.openapi.project.Project;
import llms.*;
import ui.*;
import utils.idea.IDEAEditor;
import javax.swing.*;
import java.awt.*;

import static java.awt.Cursor.WAIT_CURSOR;
import static java.awt.Cursor.getPredefinedCursor;

public class AADVController implements SendPromptListener, SourcePanelListener, ExampleListener {
   private static AADVController controller = null;
   private final Project project;
   private AADVPromptPanel promptView;
   private final AADVOutputPanel outputView;

   private OpenAIClient openAIClient
//      = new StubOpenAIClient(); // TODO change to prod
      = new OpenAIClient();
   AADVModel model = new AADVModel();
   private IDEAEditor ide = new IDEAEditor();

   private AADVController(Project project) {
      this.project = project;
      this.promptView = new AADVPromptPanel(this, this);
      this.outputView = new AADVOutputPanel();
   }

   public static void reset() {
      controller = null;
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

      model.setPromptText(text);

      promptView.getParent().setCursor(getPredefinedCursor(WAIT_CURSOR));

      new Thread(() -> {
         var files = openAIClient.retrieveCompletion(text, model.getExampleList());
         updateSourcePanels(files);
         promptView.getParent().setCursor(Cursor.getDefaultCursor());
      }).start();
   }

   @Override
   public void dump(String text) {
      System.out.println("PROMPT\n");
      System.out.println(model.combinedPrompt());
   }

   private void updateSourcePanels(Files files) {
      files.prodFiles().stream()
            .forEach(file -> upsertSourcePanel(file));
      files.testFiles().stream()
         .forEach(file -> upsertSourcePanel(file));
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

   // source panel listener methods:
   @Override
   public void applySourceToIDE(SourceFile sourceFile) {
      ide.replaceEditorContent(project, sourceFile);
   }

   @Override
   public void delete(SourceFile sourceFile) {
      var panel = model.getPanel(sourceFile).get();
      outputView.removeSourcePanel(panel);
      model.remove(panel);
   }

   // example listener methods
   @Override
   public void add(String panelName, String name, String text) {
      model.addExample(panelName, name, text);
      promptView.refreshExamples(model.getExamples());
   }

   @Override
   public void delete(String panelName) {
      model.deleteExample(panelName);
      promptView.refreshExamples(model.getExamples());
   }

   public void setPromptView(AADVPromptPanel panel) {
      this.promptView = panel;
   }

   public void setModel(AADVModel model) {
      this.model = model;
   }
}

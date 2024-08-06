package plugin;

import com.intellij.openapi.project.Project;
import llms.*;
import llms.openai.OpenAI;
import llms.openai.OpenAIClient;
import plugin.settings.AADVPluginSettings;
import ui.*;
import utils.Console;
import utils.Http;
import utils.IDGenerator;
import utils.idea.IDEAEditor;

import static java.awt.Cursor.*;

public class AADVController implements PromptListener, SourcePanelListener, ExampleListener {
   private static AADVController controller = null;
   private final Project project;
   AADVPromptPanel promptView = new AADVPromptPanel(this, this);
   private AADVOutputPanel outputView = new AADVOutputPanel();

   private final OpenAIClient openAIClient =
      new OpenAIClient(new Http(), new AADVPluginSettings());
   private OpenAI openAI = new OpenAI(openAIClient);
   private AADVModel model = new AADVModel();
   private IDEAEditor ide = new IDEAEditor();
   private IDGenerator idGenerator = new IDGenerator();
   private AADVPluginSettings aadvPluginSettings = new AADVPluginSettings();
   private final Console console = new Console();

   Thread thread;

   private AADVController(Project project) {
      this.project = project;
   }

   public static void reset() {
      controller = null;
   }

   public static synchronized AADVController get(Project project) {
      if (controller == null)
         controller = new AADVController(project);
      return controller;
   }

   public AADVOutputPanel getOutputView() {
      return outputView;
   }

   @Override
   public void send(String text) {
      var apiKey = aadvPluginSettings.retrieveAPIKey();
      if (apiKey == null) {
         promptView.showMessage(AADVPromptPanel.MSG_KEY_NOT_CONFIGURED);
         return;
      }

      model.setPromptText(text); // TODO can this be deleted

      promptView.getParent().setCursor(getPredefinedCursor(WAIT_CURSOR));

      var prompt = new Prompt(text, model.getExampleList());

      thread = new Thread(() -> {
         var files = openAI.retrieveCompletion(prompt);
         updateSourcePanels(files);
         promptView.getParent().setCursor(getDefaultCursor());
      });
      thread.start();
   }

   @Override
   public void dump() {
      console.log("PROMPT:\n");
      console.log(model.dumpPrompt());
   }

   @Override
   public void update(String text) {
      model.setPromptText(text);
   }

   // source panels

   private void updateSourcePanels(Files files) {
      files.prodFiles().forEach(this::upsertSourcePanel);
      files.testFiles().forEach(this::upsertSourcePanel);
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
   public void update(String id, String name, String text) {
      model.updateExample(id, name, text);
   }

   @Override
   public void delete(String id) {
      model.deleteExample(id);
      promptView.deleteExample(id);
   }

   @Override
   public void addNewExample() {
      var id = idGenerator.generate();
      model.addExample(id);
      var example = model.getExample(id);
      promptView.addNewExample(example);
   }

   @Override
   public void toggleEnabled(String id) {
      model.toggleEnabled(id);
      promptView.refreshExample(model.getExample(id));
   }

   public void setPromptView(AADVPromptPanel panel) {
      this.promptView = panel;
   }

   public void setModel(AADVModel model) {
      this.model = model;
   }

   void setIdGenerator(IDGenerator idGenerator) {
      this.idGenerator = idGenerator;
   }

   public void setIDEAEditor(IDEAEditor ideaEditor) {
      ide = ideaEditor;
   }

   public void setOutputView(AADVOutputPanel outputView) {
      this.outputView = outputView;
   }

   public void setAADVPluginSettings(AADVPluginSettings aadvPluginSettings) {

      this.aadvPluginSettings = aadvPluginSettings;
   }

   public void setOpenAI(OpenAI openAI) {
      this.openAI = openAI;
   }

   public Console getConsole() {
      return console;
   }
}

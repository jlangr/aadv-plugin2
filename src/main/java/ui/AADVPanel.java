package ui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import llms.OpenAIClient;
import llms.SourceFile;
import llms.StubOpenAIClient;
import utils.idea.IDEAEditor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AADVPanel extends JPanel implements SendPromptListener, UpdateFileListener {
   private final Project project;
   private final String apiKey;

   private JPanel contentPanel =  new JPanel();

   private OpenAIClient openAIClient = new StubOpenAIClient(); // TODO change to prod

   public AADVPanel(Project project, String apiKey) {
      this.project = project;
      this.apiKey = apiKey;
      layOut();
   }

   public void layOut() {
      contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

      contentPanel.add(new PromptPanel(this, apiKey));

      var scrollPane = new JBScrollPane(contentPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      setLayout(new BorderLayout());
      add(scrollPane, BorderLayout.CENTER);

      setPreferredSize(new Dimension(500, 1024));
      setMinimumSize(new Dimension(500, 1024));
   }

   @Override
   public void send(String text) {
      var files = openAIClient.retrieveCompletion(text);
      addSourcePanel(files.prodFiles());
      addSourcePanel(files.testFiles());
   }

   private void addSourcePanel(List<SourceFile> sourceFiles) {
      sourceFiles.stream()
         .map(file -> new SourcePanel(file, this))
         .forEach(panel -> {
         contentPanel.add(panel);
         contentPanel.revalidate();
         contentPanel.repaint();
      });
   }

   @Override
   public void update(SourceFile sourceFile) {
      new IDEAEditor().replaceEditorContent(project, sourceFile);
   }
}

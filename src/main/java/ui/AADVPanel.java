package ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBScrollPane;
import llms.SourceFile;
import utils.idea.IDEAEditor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AADVPanel extends JPanel implements  UpdateFileListener {
   public static final String MSG_KEY_NOT_CONFIGURED =
      "API key not configured. Please provide it in the AADV plugin settings.";
   private final Project project;
   private final SendPromptListener sendPromptListener;

   private JPanel contentPanel =  new JPanel();

   public AADVPanel(Project project, SendPromptListener sendPromptListener) {
      this.project = project;
      this.sendPromptListener = sendPromptListener;
      layOut();
   }

   public void showMessage(String message) {
      Messages.showMessageDialog(message, "Information", Messages.getInformationIcon());
   }

   public void layOut() {
      contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

      contentPanel.add(new PromptPanel(sendPromptListener));

      var scrollPane = new JBScrollPane(contentPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      setLayout(new BorderLayout());
      add(scrollPane, BorderLayout.CENTER);

      setPreferredSize(new Dimension(500, 1024));
      setMinimumSize(new Dimension(500, 1024));
   }

   public void addSourcePanels(List<SourceFile> sourceFiles) {
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

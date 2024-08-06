package ui;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBScrollPane;
import llms.Example;
import javax.swing.*;
import java.awt.*;

public class AADVPromptPanel extends JPanel {
   public static final String MSG_KEY_NOT_CONFIGURED =
      "API key not configured. Please provide it in the AADV plugin settings.";
   private final PromptListener promptListener;

   private final JPanel contentPanel =  new JPanel();
   private final ExampleListener exampleListener;
   private ExamplesPanel examplesPanel;

   public AADVPromptPanel(PromptListener promptListener, ExampleListener exampleListener) {
      this.promptListener = promptListener;
      this.exampleListener = exampleListener;
      createLayout();
   }

   public void showMessage(String message) {
      Messages.showMessageDialog(message, "Information", Messages.getInformationIcon());
   }

   public void createLayout() {
      contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

      contentPanel.add(new PromptPanel(promptListener));

      contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

      examplesPanel = new ExamplesPanel(exampleListener);
      contentPanel.add(examplesPanel);

      addScrollBar();

      setPreferredSize(new Dimension(500, 1024));
      setMinimumSize(new Dimension(500, 1024));
   }

   private void addScrollBar() {
      var scrollPane = new JBScrollPane(contentPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      setLayout(new BorderLayout());
      add(scrollPane, BorderLayout.CENTER);
   }

   public void addNewExample(Example example) {
      examplesPanel.addExamplePanel(example);
   }

   public void deleteExample(String id) {
      examplesPanel.deleteExample(id);
   }

   public void refreshExample(Example example) {
      examplesPanel.refresh(example);
   }
}

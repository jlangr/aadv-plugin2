package ui;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBScrollPane;
import llms.Example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AADVPromptPanel extends JPanel {
   public static final String MSG_KEY_NOT_CONFIGURED =
      "API key not configured. Please provide it in the AADV plugin settings.";
   private final SendPromptListener sendPromptListener;

   private JPanel contentPanel =  new JPanel();
   private ExampleListener exampleListener;
   private ExamplesPanel examplesPanel;

   public AADVPromptPanel(SendPromptListener sendPromptListener, ExampleListener exampleListener) {
      this.sendPromptListener = sendPromptListener;
      this.exampleListener = exampleListener;
      layOut();
   }

   public void showMessage(String message) {
      Messages.showMessageDialog(message, "Information", Messages.getInformationIcon());
   }

   public void layOut() {
      contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

      contentPanel.add(new PromptPanel(sendPromptListener));

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

   private void refresh() {
      contentPanel.revalidate();
      contentPanel.repaint();
   }

   public void addedExample() {
      examplesPanel.addedExample();
   }

   public void deleteExample(String panelName) {
      examplesPanel.deleteExample(panelName);
   }

   public void refreshExamples(List<Example> examples) {
      examplesPanel.refreshExamples(examples);
   }
}

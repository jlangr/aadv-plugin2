package ui;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBScrollPane;
import javax.swing.*;
import java.awt.*;

public class AADVPanel extends JPanel {
   public static final String MSG_KEY_NOT_CONFIGURED =
      "API key not configured. Please provide it in the AADV plugin settings.";
   private final SendPromptListener sendPromptListener;

   private JPanel contentPanel =  new JPanel();
   private ExampleListener exampleListener;

   public AADVPanel(SendPromptListener sendPromptListener, ExampleListener exampleListener) {
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

      contentPanel.add(new ExamplesPanel(exampleListener));

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

   public void addSourcePanel(SourcePanel panel) {
      contentPanel.add(panel);
      refresh();
   }

   public void removeSourcePanel(SourcePanel panel) {
      contentPanel.remove(panel);
      refresh();
   }

   private void refresh() {
      contentPanel.revalidate();
      contentPanel.repaint();
   }
}

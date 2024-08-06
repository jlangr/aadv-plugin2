package plugin;

import com.intellij.ui.components.JBScrollPane;
import ui.SourcePanel;

import javax.swing.*;
import java.awt.*;

public class AADVOutputPanel extends JPanel{
   private final JPanel contentPanel =  new JPanel();

   public AADVOutputPanel() {
      layOut();
   }

   @Override
   protected boolean requestFocusInWindow(boolean temporary) {
      return super.requestFocusInWindow(temporary);
   }

   public void layOut() {
      contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

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

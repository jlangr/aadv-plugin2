package ui;

import com.intellij.ui.components.JBScrollPane;
import llms.Example;
import utils.UI;
import javax.swing.*;
import java.awt.*;

public class ExamplesPanel extends JPanel {
   public static final String MSG_EXAMPLES = "Examples";
   private static final String MSG_ADD = "Add";
   private final ExampleListener exampleListener;
   private final JPanel examplePanels = new JPanel();

   JButton addExampleButton;

   public ExamplesPanel(ExampleListener exampleListener) {
      this.exampleListener = exampleListener;

      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      add(createHeaderRow());

      add(Box.createRigidArea(new Dimension(0, 5)));

      examplePanels.setLayout(new BoxLayout(examplePanels, BoxLayout.Y_AXIS));
      add(new JBScrollPane(examplePanels));

      // TODO hmm
      var exampleField = new JTextArea();
      var preferredHeight = UI.calculatePreferredHeight(exampleField, 5);
      setPreferredSize(new Dimension(400, preferredHeight));
      setMinimumSize(new Dimension(400, preferredHeight));
   }

   private JPanel createHeaderRow() {
      var panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

      addExampleButton = UI.createIconButton("plus.png", MSG_ADD,
         e -> exampleListener.addNewExample());
      panel.add(addExampleButton);

      panel.add(UI.createHeaderLabel(MSG_EXAMPLES));

      return panel;
   }

   public void addExamplePanel(Example example) {
      examplePanels.add(new ExamplePanel(exampleListener, example));
      refresh();
   }

   public void deleteExample(String id) {
      var panel = getExamplePanel(id);
      if (panel != null) {
         examplePanels.remove(panel);
         refresh();
      }
   }

   public void refresh(Example example) {
      var panel = getExamplePanel(example.id());
      if (panel != null)
         panel.refresh(example);
   }

   public void refresh() {
      this.revalidate();
      this.repaint();
   }

   private ExamplePanel getExamplePanel(String id) {
      for (var component: examplePanels.getComponents()) {
         if (component == null || component.getClass() != ExamplePanel.class)
            continue;
         var examplePanel = (ExamplePanel)component;
         if (examplePanel.getExampleId().equals(id))
            return examplePanel;
      }
      return null;
   }
}

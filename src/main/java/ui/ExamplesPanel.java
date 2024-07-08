package ui;

import javax.swing.*;
import java.awt.*;

public class ExamplesPanel extends JPanel {
   public static final String MSG_EXAMPLES = "Examples";
   private ExamplePanel newExamplePanel;

   public ExamplesPanel() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      add(new JLabel(MSG_EXAMPLES));
      // iterate model examples here
      addEmptyExample();

      var preferredHeight = newExamplePanel.preferredHeight();
      setPreferredSize(new Dimension(400, preferredHeight));
      setMinimumSize(new Dimension(400, preferredHeight));
      setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
   }

   private void addEmptyExample() {
      newExamplePanel = new ExamplePanel();
      this.add(newExamplePanel);
   }
}

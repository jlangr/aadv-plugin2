package ui;

import javax.swing.*;
import java.awt.*;

public class ExamplesPanel extends JPanel {
   public static final String MSG_EXAMPLES = "Examples";
   private final ExampleListener exampleListener;
   private ExamplePanel newExamplePanel;

   public ExamplesPanel(ExampleListener exampleListener) {
      this.exampleListener = exampleListener;

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
      newExamplePanel = new ExamplePanel(exampleListener);
      this.add(newExamplePanel);
   }
}

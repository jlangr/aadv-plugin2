package ui;

import llms.Example;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ExamplesPanel extends JPanel {
   public static final String MSG_EXAMPLES = "Examples";
   private final ExampleListener exampleListener;
   private ExamplePanel newExamplePanel;

   public ExamplesPanel(ExampleListener exampleListener) {
      this.exampleListener = exampleListener;

      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      add(new JLabel(MSG_EXAMPLES));

      newExamplePanel = addEmptyExample();

      var preferredHeight = newExamplePanel.preferredHeight();
      setPreferredSize(new Dimension(400, preferredHeight));
      setMinimumSize(new Dimension(400, preferredHeight));
   }

   private ExamplePanel addEmptyExample() {
      System.out.println("addEmptyExample");
      var panel = new ExamplePanel(exampleListener);
      add(panel);
      return panel;
   }

   public void refresh() {
      this.revalidate();
      this.repaint();
   }

   public void refreshExamples(List<Example> examples) {
      this.removeAll();

      examples.stream()
         .forEach(example -> {
            var panel = new ExamplePanel(exampleListener);
            panel.setText(example.getText());
            add(example.getId(), panel);
         });

      var emptyPanel = addEmptyExample();
      emptyPanel.requestFocus();

      refresh();
   }

   public void addedExample() {
      System.out.println("Added example");
      addEmptyExample();
      refresh(); // TODO no need for arg?
   }

   public void deleteExample(String panelName) {
      var panel = Arrays.stream(this.getComponents())
         .filter(component -> component instanceof ExamplePanel && panelName.equals(component.getName()))
         .findFirst();
      if (panel.isEmpty()) {
         System.out.println("panel " + panelName + " not present");
         return;
      }
      this.remove(panel.get());
      refresh();
   }

}

package ui;

import llms.Example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamplesPanel extends JPanel {
   public static final String MSG_EXAMPLES = "Examples";
   private final ExampleListener exampleListener;
   private ExamplePanel newExamplePanel;

   public static void main(String[] args) {
      // Schedule a job for the event-dispatching thread:
      // creating and showing this application's GUI.
      SwingUtilities.invokeLater(() -> createAndShowGUI());
   }

   private static void createAndShowGUI() {
      JFrame frame = new JFrame("Simple Swing Application");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(400, 900);

      var examples = new ArrayList<Example>();
      examples.add(new Example("one", "a one"));
      examples.add(new Example("two", "a two"));

      ExampleListener listener = new ExampleListener() {
         @Override
         public void add(String panelName, String text) {
            System.out.println("add " + panelName + " " + text);
         }

         @Override
         public void delete(String name) {
            System.out.println("delete " + name);
         }
      };
      var panel = new ExamplesPanel(listener);
      panel.refreshExamples(examples);
      frame.getContentPane().add(panel, BorderLayout.CENTER);
      frame.setVisible(true);
   }

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
      var panel = new ExamplePanel(exampleListener, Example.EMPTY);
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
            var panel = new ExamplePanel(exampleListener, example);
            panel.setText(example.getText());
            add(example.getId(), panel);
         });

      var emptyPanel = addEmptyExample();
      emptyPanel.requestFocus();

      refresh();
   }

   public void addedExample() {
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

package ui;

import llms.Example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
            panel.setExampleText(example.getText());
            panel.setExampleName(example.getName());
            panel.setName(example.getId());
            add(example.getId(), panel);
         });

      var emptyPanel = addEmptyExample();
      emptyPanel.requestFocus();

      refresh();
   }

   // Runner for rendering humble UI:

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> createAndShowGUI());
   }

   static void createAndShowGUI() {
      JFrame frame = new JFrame("Simple Swing Application");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(400, 900);

      var examples = new ArrayList<Example>();
      examples.add(new Example("1", "one", "a one"));
      examples.add(new Example("2", "two", "a two"));

      var panel = createExamplesPanel();
      panel.refreshExamples(examples);
      frame.getContentPane().add(panel, BorderLayout.CENTER);
      frame.setVisible(true);
   }

   static ExamplesPanel createExamplesPanel() {
      var listener = new ExampleListener() {
         @Override
         public void add(String panelName, String name, String text) {
            System.out.println("add " + panelName + " name: " + name + " > " + text);
         }

         @Override
         public void upsert(String panelName, String name, String text) {
            System.out.println("update " + panelName + " name: " + name + " > " + text);
         }

         @Override
         public void delete(String name) {
            System.out.println("delete " + name);
         }
      };
      return new ExamplesPanel(listener);
   }
}

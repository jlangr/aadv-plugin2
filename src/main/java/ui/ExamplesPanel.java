package ui;

import llms.Example;
import utils.UI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExamplesPanel extends JPanel {
   public static final String MSG_EXAMPLES = "Examples";
   private final ExampleListener exampleListener;

   public ExamplesPanel(ExampleListener exampleListener) {
      this.exampleListener = exampleListener;

      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      add(UI.createHeaderLabel(MSG_EXAMPLES));

      add(Box.createRigidArea(new Dimension(0, 20)));

      createAddExampleButton();
      add(addExampleButton);

      // TODO hmm
      var exampleField = new JTextArea();
      var preferredHeight = UI.calculatePreferredHeight(exampleField, 5);
      setPreferredSize(new Dimension(400, preferredHeight));
      setMinimumSize(new Dimension(400, preferredHeight));
   }

   JButton addExampleButton;
   private static final String MSG_ADD = "Add";

   private void createAddExampleButton() {
      addExampleButton = UI.createIconButton(this, "plus.png", MSG_ADD,
         e -> exampleListener.addNewExample());
   }

   public void addEmptyExample(String name) {
      add(new ExamplePanel(exampleListener, name, Example.EMPTY));
      refresh();
   }

   public void refresh() {
      this.revalidate();
      this.repaint();
   }

   public void refreshExamples(List<Example> examples) {

//      this.remove()

//      examples.stream()
//         .forEach(example -> {
//            var panel = new ExamplePanel(exampleListener, example.getId(), example);
//            panel.setExampleText(example.getText());
//            panel.setExampleName(example.getName());
//            panel.setName(example.getId());
//            add(example.getId(), panel);
//         });
//
      refresh();
   }

   public void deleteExample(String name) {
      for (var component: getComponents())
         if (component != null && component.getName() != null && component.getName().equals(name)) {
            remove(component);
            break;
         }
      refresh();
   }
}

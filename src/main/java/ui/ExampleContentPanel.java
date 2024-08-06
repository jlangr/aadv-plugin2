package ui;

import com.intellij.ui.components.JBScrollPane;
import llms.Example;
import utils.UI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

class ExampleContentPanel extends JPanel {
   private Example example;
   private final ExampleListener exampleListener;

   JTextArea exampleField;
   private final EditableLabel nameLabel;
   private final JLabel disabledLabel = new JLabel("");

   ExampleContentPanel(ExampleListener exampleListener, Example example) {
      this.example = example;
      this.exampleListener = exampleListener;

      nameLabel = createNameLabel(example);
      exampleField = createExampleField();

      createLayout();
   }

   private void createLayout() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      setAlignmentX(Component.LEFT_ALIGNMENT);

      var header = new JPanel(new FlowLayout(FlowLayout.LEFT));
      header.add(nameLabel);
      header.setAlignmentX(LEFT_ALIGNMENT);
      header.add(disabledLabel);
      add(header);

      add(Box.createRigidArea(new Dimension(0, 5)));

      var scrollPane = new JBScrollPane(exampleField);
      scrollPane.setAlignmentX(LEFT_ALIGNMENT);
      add(scrollPane);
   }

   private JTextArea createExampleField() {
      var exampleField = UI.createTextArea(8, 80, (e) -> {});
      exampleField.getDocument().addDocumentListener(new DocumentListener() {
         @Override
         public void insertUpdate(DocumentEvent e) {
            notifyExampleListener();
         }

         @Override
         public void removeUpdate(DocumentEvent e) {
            notifyExampleListener();
         }

         @Override
         public void changedUpdate(DocumentEvent e) {
            notifyExampleListener();
         }
      });
      return exampleField;
   }

   private EditableLabel createNameLabel(Example example) {
      return new EditableLabel(nameLabelText(example),
         text -> exampleListener.update(example.id(), text, exampleField.getText()));
   }

   private String nameLabelText(Example example) {
      return example == Example.EMPTY || isEmpty(example.name())
         ? ExamplePanel.MSG_NAME_PLACEHOLDER
         : example.name();
   }

   private void notifyExampleListener() {
      exampleListener.update(example.id(), nameLabel.getText(), exampleField.getText());
   }

   private boolean isEmpty(String s) {
      return s == null || s.isEmpty();
   }

   public void refresh(Example example) {
      this.example = example;
      nameLabel.setText(nameLabelText(example));
      disabledLabel.setText(example.isEnabled() ? "" : "[ disabled ]");
   }
}

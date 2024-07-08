package ui;

import utils.UI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;

import java.awt.*;
import java.util.UUID;

import static utils.UI.setButtonHeight;

public class ExamplePanel extends JPanel {
   private static final String MSG_ADD = "Add";
   private final ExampleListener exampleListener;
   private JTextArea exampleField;
   private JButton addExampleButton;
   private JButton deleteExampleButton;

   public ExamplePanel(ExampleListener exampleListener) {
      this.exampleListener = exampleListener;

      setName(UUID.randomUUID().toString());
      System.out.println("in ctor for Example panel; name: " + getName());

      createExampleField();
      createAddExampleButton();
      createDeleteExampleButton();

      setLayout(new GridBagLayout());
      var constraints = new GridBagConstraints();

      constraints.fill = GridBagConstraints.BOTH;
      constraints.insets = new Insets(5, 5, 5, 5);

      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.weightx = 1.0;
      constraints.weighty = 0.1;
      constraints.gridwidth = 2;
      add(exampleField, constraints);

      constraints.gridx = 2;
      constraints.gridy = 0;
      constraints.weightx = 0.0;
      constraints.gridwidth = 1;
      constraints.fill = GridBagConstraints.NONE;
      add(addExampleButton, constraints);

      constraints.gridx = 2;
      constraints.gridy = 0;
      constraints.weightx = 0.0;
      constraints.gridwidth = 1;
      constraints.fill = GridBagConstraints.NONE;
      add(deleteExampleButton, constraints);
   }

   private void createDeleteExampleButton() {
      deleteExampleButton = new JButton(MSG_ADD);
      deleteExampleButton.addActionListener( e -> exampleListener.delete(getName()));
      deleteExampleButton.setEnabled(hasText());
      setButtonHeight(deleteExampleButton);
   }

   private void createAddExampleButton() {
      addExampleButton = new JButton(MSG_ADD);
      addExampleButton.addActionListener( e -> {
         System.out.println("ExamplePanel::actionListener called " + getName());
         exampleListener.add(exampleField.getText(), getName());
      });
      addExampleButton.setEnabled(hasText());
      setButtonHeight(addExampleButton);
   }

   // dup
   private boolean hasText() {
      return !exampleField.getText().isBlank();
   }

   private void createExampleField() {
      exampleField = UI.createTextArea(5, 80, this::updateButtonState);
   }

   private void updateButtonState(DocumentEvent documentEvent) {
      addExampleButton.setEnabled(!exampleField.getText().isBlank());
   }

   int preferredHeight() {
      return UI.calculatePreferredHeight(exampleField, 5);
   }

   public void setText(String text) {
      exampleField.setText(text);
   }
}

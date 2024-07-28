package ui;

import llms.Example;
import utils.UI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.UUID;

import static java.awt.BorderLayout.*;
import static javax.swing.BorderFactory.createEmptyBorder;

public class ExamplePanel extends JPanel {
   private static final String MSG_ADD = "Add";
   private static final String MSG_DELETE = "Delete";
   public static final String MSG_NAME_PLACEHOLDER = "[ add name ]";
   private final ExampleListener exampleListener;
   private final Example example;
   private JTextArea exampleField;
   private EditableLabel nameLabel;
   private JButton addExampleButton;
   private JButton deleteExampleButton;

   public ExamplePanel(ExampleListener exampleListener, Example example) {
      this.exampleListener = exampleListener;
      this.example = example;

      setName(UUID.randomUUID().toString());

      setLayout(new BorderLayout());
      add(createButtonPanel(), EAST);
      add(createContentPanel(), CENTER);

      setBorder(createEmptyBorder(5, 5, 5, 5));

      int preferredHeight = calculatePreferredHeight(exampleField, 3);
      setPreferredSize(new Dimension(400, preferredHeight));
      setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
   }

   private JPanel createContentPanel() {
      createExampleField();

      var panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setAlignmentX(Component.LEFT_ALIGNMENT);

      nameLabel = new EditableLabel(example == Example.EMPTY || isEmpty(example.getName()) ? MSG_NAME_PLACEHOLDER : example.getName());
      nameLabel.setAlignmentX(LEFT_ALIGNMENT);
      panel.add(nameLabel);

      panel.add(Box.createRigidArea(new Dimension(0, 5)));

      exampleField.setAlignmentX(LEFT_ALIGNMENT);
      panel.add(exampleField);
      return panel;
   }

   private boolean isEmpty(String s) {
      return s == null || s.isEmpty();
   }

   // dup?
   private int calculatePreferredHeight(JTextArea textArea, int rows) {
      var fm = textArea.getFontMetrics(textArea.getFont());
      var rowHeight = fm.getHeight();
      var insets = textArea.getInsets();
      return insets.top + insets.bottom + rowHeight * rows;
   }

   private JPanel createButtonPanel() {
      var buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

      createDeleteExampleButton();
      createAddExampleButton();

      buttonPanel.add(example == Example.EMPTY ? addExampleButton : deleteExampleButton);
      return buttonPanel;
   }

   private void createDeleteExampleButton() {
      deleteExampleButton = UI.createIconButton(this, "close_icon.png", MSG_DELETE,
            e -> exampleListener.delete(getName()));
   }

   private void createAddExampleButton() {
      addExampleButton = UI.createIconButton(this, "plus.png", MSG_ADD,
         e -> {
            System.out.println("adding example " + nameLabel.getText());
         exampleListener.add(getName(), nameLabel.getText(), exampleField.getText());
         });
      addExampleButton.setEnabled(hasText());
      // TODO how to show different states enabled / disabled
   }

   // dup
   private boolean hasText() {
      return exampleField != null && !exampleField.getText().isBlank();
   }

   private void createExampleField() {
      exampleField = UI.createTextArea(3, 80, this::updateButtonState);
   }

   private void updateButtonState(DocumentEvent documentEvent) {
      addExampleButton.setEnabled(!exampleField.getText().isBlank());
   }

   int preferredHeight() {
      return UI.calculatePreferredHeight(exampleField, 5);
   }

   public void setExampleText(String text) {
      exampleField.setText(text);
   }

   public void setExampleName(String name) {
      nameLabel.setText(name);
   }
}

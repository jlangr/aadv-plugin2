package ui;

import com.intellij.ui.components.JBScrollPane;
import llms.Example;
import utils.UI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

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
   private JButton deleteExampleButton;

   public ExamplePanel(ExampleListener exampleListener, String name, Example example) {
      this.exampleListener = exampleListener;
      this.example = example;
      setName(name);

      setLayout(new BorderLayout());
      add(createButtonPanel(), EAST);
      add(createContentPanel(), CENTER);

      setBorder(createEmptyBorder(5, 5, 5, 5));

      // dup?
      int preferredHeight = calculatePreferredHeight(exampleField, 5);
      setPreferredSize(new Dimension(400, preferredHeight));
      setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
   }

   private void notifyExampleListener() {
      exampleListener.upsert(getName(), nameLabel.getText(), exampleField.getText());
   }

   private JPanel createContentPanel() {
      createExampleField();

      var panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setAlignmentX(Component.LEFT_ALIGNMENT);

      var initialText = example == Example.EMPTY || isEmpty(example.getName())
         ? MSG_NAME_PLACEHOLDER
         : example.getName();

      nameLabel = new EditableLabel(initialText,
         text -> exampleListener.upsert(getName(), text, exampleField.getText()));
      nameLabel.setAlignmentX(LEFT_ALIGNMENT);
      panel.add(nameLabel);

      panel.add(Box.createRigidArea(new Dimension(0, 5)));

      exampleField.setAlignmentX(LEFT_ALIGNMENT);
      var scrollPane = new JBScrollPane(exampleField);
      scrollPane.setAlignmentX(LEFT_ALIGNMENT);
      panel.add(scrollPane);
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

      deleteExampleButton = UI.createIconButton(this, "close_icon.png", MSG_DELETE,
         e -> exampleListener.delete(getName()));

      buttonPanel.add(deleteExampleButton);
      return buttonPanel;
   }

   private void createExampleField() {
      exampleField = UI.createTextArea(3, 80, (e) -> {});
      exampleField.getDocument().addDocumentListener(new DocumentListener() {
         @Override
         public void insertUpdate(DocumentEvent e) { notifyExampleListener(); }

         @Override
         public void removeUpdate(DocumentEvent e) { notifyExampleListener(); }

         @Override
         public void changedUpdate(DocumentEvent e) { notifyExampleListener(); }
      });
   }

   public void setExampleText(String text) {
      exampleField.setText(text);
   }

   public void setExampleName(String name) {
      nameLabel.setText(name);
   }
}

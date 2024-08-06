package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditableLabel extends JPanel {
   public static final String CHECK_ICON = "✓";
   public static final String CANCEL_ICON = "✗";
   private final FieldListener fieldListener;
   private final JLabel label;
   private final JTextField textField;
   private final JButton checkButton;
   private final JButton cancelButton;
   private String originalText;

   public EditableLabel(String initialText, FieldListener fieldListener) {
      this.fieldListener = fieldListener;

      setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
      originalText = initialText;

      label = new JLabel(initialText);
      label.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            showTextField();
         }
      });

      textField = new JTextField(initialText, 20);
      checkButton = new JButton(CHECK_ICON);
      cancelButton = new JButton(CANCEL_ICON);

      checkButton.addActionListener(this::confirmEdit);
      cancelButton.addActionListener(this::cancelEdit);

      add(label);
   }

   public String getText() {
      return label.getText();
   }

   private void showTextField() {
      removeAll();
      add(textField);
      add(checkButton);
      add(cancelButton);
      revalidate();
      repaint();
      textField.requestFocus();
      textField.selectAll();
   }

   private void confirmEdit(ActionEvent e) {
      originalText = textField.getText();
      label.setText(originalText);
      fieldListener.updated(originalText);
      showLabel();
      label.requestFocus();
   }

   private void cancelEdit(ActionEvent e) {
      textField.setText(originalText);
      showLabel();
   }

   private void showLabel() {
      removeAll();
      add(label);
      revalidate();
      repaint();
   }

   public void setText(String text) {
      textField.setText(text);
   }
}

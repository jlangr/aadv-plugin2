package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditableLabel extends JPanel {
   private JLabel label;
   private JTextField textField;
   private JButton checkButton;
   private JButton cancelButton;
   private String originalText;

   public EditableLabel(String initialText) {
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
      checkButton = new JButton("\u2713"); // Unicode for check icon
      cancelButton = new JButton("\u2717"); // Unicode for cancel icon

      checkButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            confirmEdit();
         }
      });

      cancelButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            cancelEdit();
         }
      });

      add(label);
   }

   private void showTextField() {
      removeAll();
      add(textField);
      add(checkButton);
      add(cancelButton);
      revalidate();
      repaint();
      textField.requestFocus();
   }

   private void confirmEdit() {
      originalText = textField.getText();
      label.setText(originalText);
      showLabel();
   }

   private void cancelEdit() {
      textField.setText(originalText);
      showLabel();
   }

   private void showLabel() {
      removeAll();
      add(label);
      revalidate();
      repaint();
   }
}

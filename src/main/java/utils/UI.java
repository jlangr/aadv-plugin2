package utils;

import ui.JTextAreaDocumentListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.function.Consumer;

public class UI {
   public static void setButtonHeight(JButton button) {
      var fm = button.getFontMetrics(button.getFont());
      int textHeight = fm.getHeight();
      int padding = 10; // Adjust padding as needed
      int buttonHeight = textHeight + padding;
      button.setPreferredSize(new Dimension(button.getPreferredSize().width, buttonHeight));
      button.setMinimumSize(new Dimension(button.getMinimumSize().width, buttonHeight));
      button.setMaximumSize(new Dimension(button.getMaximumSize().width, buttonHeight));
   }


   public static int calculatePreferredHeight(JTextArea field, int lineCount) {
      var metrics = field.getFontMetrics(field.getFont());
      return metrics.getHeight() * lineCount
         + field.getInsets().top + field.getInsets().bottom;
   }

   public static JTextArea createTextArea(int rows, int columns, Consumer<DocumentEvent> listener) {
      var exampleField = new JTextArea(rows, columns);
      exampleField.getDocument().addDocumentListener(
         new JTextAreaDocumentListener(listener));
      exampleField.setEditable(true);
      exampleField.setLineWrap(true);
      exampleField.setWrapStyleWord(true);
      exampleField.setMargin(new Insets(10, 10, 10, 10));
      return exampleField;
   }
}

package utils;

import com.intellij.util.ui.JBUI;
import ui.JTextAreaDocumentListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class UI {
   public static void setButtonHeight(JButton button) {
      var fm = button.getFontMetrics(button.getFont());
      int textHeight = fm.getHeight();
      int padding = 10;
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
      exampleField.setMargin(JBUI.insets(10));
      return exampleField;
   }

   public static void embiggen(JLabel label) {
      var currentFont = label.getFont();
      label.setFont(currentFont.deriveFont(currentFont.getStyle(), 24f));
   }

   public static JLabel createHeaderLabel(String text) {
      var label = new JLabel(text);
      embiggen(label);
      return label;
   }

   public static JButton createIconButton(
      String imageFilename, String toolTipText, ActionListener listener) {
      var imageIcon = ImageLoader.loadImageIcon("/images/" + imageFilename);
      return createIconButton(imageIcon, toolTipText, listener);
   }

   public static JButton createIconButton(ImageIcon icon, String toolTipText, ActionListener listener) {
      var button = new JButton(icon);
      button.setToolTipText(toolTipText);
      button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      button.setContentAreaFilled(false);
      button.setBorderPainted(false);
      button.setFocusPainted(false);
      button.setPreferredSize(new Dimension(16, 16));
      button.addActionListener(listener);
      return button;
   }
}

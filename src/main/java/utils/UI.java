package utils;

import javax.swing.*;
import java.awt.*;

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
}

package ui;

import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

public class Chip extends JPanel {
   private final String text;
   private final Color backgroundColor;

   public Chip(String text, Color backgroundColor) {
      this.text = text;
      this.backgroundColor = backgroundColor;
      setOpaque(false); // Ensure the background is painted
      setFont(new Font("Arial", Font.PLAIN, 12)); // Set a small font
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;

      // Anti-aliasing for smooth text and graphics
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      // Set the background color and fill the rounded rectangle
      g2d.setColor(backgroundColor);
      g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

      // Set the text color and draw the text in the center
      g2d.setColor(JBColor.WHITE);
      FontMetrics fm = g2d.getFontMetrics();
      int textWidth = fm.stringWidth(text);
      int textHeight = fm.getAscent();
      int x = (getWidth() - textWidth) / 2;
      int y = (getHeight() + textHeight) / 2 - fm.getDescent();
      g2d.drawString(text, x, y);
   }

   @Override
   public Dimension getPreferredSize() {
      FontMetrics fm = getFontMetrics(getFont());
      int textWidth = fm.stringWidth(text);
      int textHeight = fm.getHeight();
      return new Dimension(textWidth + 20, textHeight + 10); // Add padding around the text
   }

   @Override
   public Dimension getMinimumSize() {
      return new Dimension(50, 30); // Set a minimum width to ensure it doesn't get chopped
   }
}

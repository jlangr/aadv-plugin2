package ui;

import llms.Example;
import utils.UI;

import javax.swing.*;
import java.awt.*;
import static java.awt.BorderLayout.*;
import static javax.swing.BorderFactory.createEmptyBorder;

public class ExamplePanel extends JPanel {
   private static final String MSG_DELETE = "Delete";
   private static final String MSG_PAUSE = "Temporarily disable example";
   private static final String MSG_PLAY = "Reactivate example";
   public static final String MSG_NAME_PLACEHOLDER = "[ add name ]";
   public static final String IMG_CIRCLE_PAUSE = "Circle-Pause.png";
   public static final String IMG_CIRCLE_PLAY = "Circle-Play.png";

   private final ImageIcon disableIcon =
      new ImageIcon(ExamplePanel.class.getResource(IMG_CIRCLE_PAUSE));
   private final ImageIcon enableIcon =
      new ImageIcon(ExamplePanel.class.getResource(IMG_CIRCLE_PLAY));

   private final ExampleListener exampleListener;
   private final ExampleContentPanel exampleContentPanel;
   private Example example;
   private JButton deleteExampleButton;
   private JButton toggleEnabledButton;

   public ExamplePanel(ExampleListener exampleListener, Example example) {
      this.exampleListener = exampleListener;
      this.example = example;
      exampleContentPanel = new ExampleContentPanel(exampleListener, example);

      setLayout(new BorderLayout());
      add(createButtonPanel(), EAST);
      add(exampleContentPanel, CENTER);

      setBorder(createEmptyBorder(5, 5, 5, 5));

      // dup?
      // TODO ugh accessing content panel field
      int preferredHeight = calculatePreferredHeight(exampleContentPanel.exampleField, 5);
      setPreferredSize(new Dimension(400, preferredHeight));
      setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
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
         e -> exampleListener.delete(example.getId()));
      buttonPanel.add(deleteExampleButton);

      toggleEnabledButton = UI.createIconButton(getEnabledButtonIcon(), getImageTooltip(),
         e -> exampleListener.toggleEnabled(example.getId()));
      buttonPanel.add(toggleEnabledButton);

      return buttonPanel;
   }

   private ImageIcon getEnabledButtonIcon() {
      return example.isEnabled() ? disableIcon : enableIcon;
   }

   private String getImageTooltip() {
      return example.isEnabled() ? MSG_PAUSE : MSG_PLAY;
   }

   public void refresh(Example example) {
      this.example = example;
      exampleContentPanel.refresh(example);
      toggleEnabledButton.setIcon(getEnabledButtonIcon());
      toggleEnabledButton.setToolTipText(getToolTipText());
   }
}

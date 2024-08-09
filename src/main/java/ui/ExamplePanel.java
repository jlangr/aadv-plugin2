package ui;

import llms.Example;
import utils.UI;
import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.*;
import static java.util.Objects.requireNonNull;
import static javax.swing.BorderFactory.createEmptyBorder;

public class ExamplePanel extends JPanel {
   private static final String MSG_DELETE = "Delete";
   private static final String MSG_PAUSE = "Temporarily disable example";
   private static final String MSG_PLAY = "Reactivate example";
   public static final String MSG_NAME_PLACEHOLDER = "[ add name ]";
   public static final String IMG_CIRCLE_PAUSE = "Circle-Pause.png";
   public static final String IMG_CIRCLE_PLAY = "Circle-Play.png";

   private final ImageIcon disableIcon =
      new ImageIcon(requireNonNull(ExamplePanel.class.getResource(IMG_CIRCLE_PAUSE)));
   private final ImageIcon enableIcon =
      new ImageIcon(requireNonNull(ExamplePanel.class.getResource(IMG_CIRCLE_PLAY)));

   private final ExampleListener exampleListener;
   private final ExampleContentPanel exampleContentPanel;
   private Example example;
   private JButton toggleEnabledButton;

   public ExamplePanel(ExampleListener exampleListener, Example example) {
      this.exampleListener = exampleListener;
      this.example = example;
      exampleContentPanel = new ExampleContentPanel(exampleListener, example);

      setLayout(new BorderLayout());
      add(createButtonPanel(), EAST);
      add(exampleContentPanel, CENTER);

      setBorder(createEmptyBorder(5, 5, 5, 5));

      // TODO ugh accessing content panel field
      int preferredHeight = UI.calculatePreferredHeight(exampleContentPanel.exampleField, 5);
      setPreferredSize(new Dimension(400, preferredHeight));
      setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
   }

   private JPanel createButtonPanel() {
      var buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

      var deleteExampleButton = UI.createIconButton("close_icon.png", MSG_DELETE,
         e -> exampleListener.delete(example.id()));
      buttonPanel.add(deleteExampleButton);

      toggleEnabledButton = UI.createIconButton(getEnabledButtonIcon(), getImageTooltip(),
         e -> exampleListener.toggleEnabled(example.id()));
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

   public String getExampleId() {
      return example.id();
   }
}

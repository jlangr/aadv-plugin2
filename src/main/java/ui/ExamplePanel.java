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
   private Example example;
   private JTextArea exampleField;
   private EditableLabel nameLabel;
   private JButton deleteExampleButton;
   private JButton toggleEnabledButton;

   public ExamplePanel(ExampleListener exampleListener, String id, Example example) {
      this.exampleListener = exampleListener;
      this.example = example;
      setName(id);

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
      exampleField = createExampleField();
      nameLabel = createNameLabel();

      var panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setAlignmentX(Component.LEFT_ALIGNMENT);

      nameLabel.setAlignmentX(LEFT_ALIGNMENT);
      panel.add(nameLabel);

      panel.add(Box.createRigidArea(new Dimension(0, 5)));

      var scrollPane = new JBScrollPane(exampleField);
      scrollPane.setAlignmentX(LEFT_ALIGNMENT);
      panel.add(scrollPane);
      return panel;
   }

   private EditableLabel createNameLabel() {
      var initialText = example == Example.EMPTY || isEmpty(example.getName())
         ? MSG_NAME_PLACEHOLDER
         : example.getName();

      var nameLabel = new EditableLabel(initialText,
         text -> exampleListener.upsert(getName(), text, exampleField.getText()));
      return nameLabel;
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

      toggleEnabledButton = UI.createIconButton(getEnabledButtonIcon(), getImageTooltip(),
         e -> exampleListener.toggleEnabled(getName()));
      buttonPanel.add(toggleEnabledButton);

      return buttonPanel;
   }

   private ImageIcon getEnabledButtonIcon() {
      return example.isEnabled() ? disableIcon : enableIcon;
   }

   private String getImageTooltip() {
      return example.isEnabled() ? MSG_PAUSE : MSG_PLAY;
   }

   private JTextArea createExampleField() {
      var exampleField = UI.createTextArea(8, 80, (e) -> {});
      exampleField.getDocument().addDocumentListener(new DocumentListener() {
         @Override
         public void insertUpdate(DocumentEvent e) { notifyExampleListener(); }

         @Override
         public void removeUpdate(DocumentEvent e) { notifyExampleListener(); }

         @Override
         public void changedUpdate(DocumentEvent e) { notifyExampleListener(); }
      });
      return exampleField;
   }

   public void refresh(Example example) {
      this.example = example;
      toggleEnabledButton.setIcon(getEnabledButtonIcon());
      toggleEnabledButton.setToolTipText(getToolTipText());
      // TODO any others? not yet
   }
}

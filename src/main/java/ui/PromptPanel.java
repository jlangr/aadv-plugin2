package ui;

import com.intellij.ui.components.JBScrollPane;
import utils.UI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import static utils.UI.setButtonHeight;

public class PromptPanel extends JPanel {
   public static final int PROMPT_FIELD_LINE_COUNT = 10;
   public static final String MSG_SEND_PROMPT = "Send";
   public static final String MSG_DUMP_PROMPT = "Dump";
   private final SendPromptListener sendPromptListener;
   private JTextArea promptField;
   private JButton sendPromptButton;
   private JButton dumpPromptButton;

   public PromptPanel(SendPromptListener sendPromptListener) {
      this.sendPromptListener = sendPromptListener;

      createPromptField();

      setLayout(new BorderLayout());

      add(new JBScrollPane(promptField), BorderLayout.CENTER);
      add(createButtonPanel(), BorderLayout.EAST);

      // dup?
      var preferredHeight = UI.calculatePreferredHeight(promptField, PROMPT_FIELD_LINE_COUNT);
      setPreferredSize(new Dimension(400, preferredHeight));
      setMinimumSize(new Dimension(400, preferredHeight));
      setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
   }

   private JPanel createButtonPanel() {
      createPromptButton();
      createDumpPromptButton();

      var panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.add(Box.createVerticalGlue());
      panel.add(sendPromptButton);
      panel.add(Box.createRigidArea(new Dimension(0, 10)));
      panel.add(dumpPromptButton);
      panel.add(Box.createVerticalGlue());
      return panel;
   }

   private void createPromptField() {
      promptField = UI.createTextArea(5, 80, this::updateButtonState);
      // TODO delete:
//      promptField = new JTextArea(5, 80);
//      promptField.getDocument().addDocumentListener(
//         new JTextAreaDocumentListener(this::updateButtonState));
//      promptField.setEditable(true);
//      promptField.setLineWrap(true);
//      promptField.setWrapStyleWord(true);
//      promptField.setMargin(new Insets(10, 10, 10, 10));
   }

   private void updateButtonState(DocumentEvent documentEvent) {
      sendPromptButton.setEnabled(!promptField.getText().isBlank());
   }

   private void createPromptButton() {
      sendPromptButton = new JButton(MSG_SEND_PROMPT);
      sendPromptButton.addActionListener(e -> sendPromptListener.send(promptField.getText()));
      sendPromptButton.setEnabled(isPromptTextAvailable());
      setButtonHeight(sendPromptButton);
   }

   private void createDumpPromptButton() {
      dumpPromptButton = new JButton(MSG_DUMP_PROMPT);
      dumpPromptButton.addActionListener( e -> sendPromptListener.dump(promptField.getText()));
      setButtonHeight(dumpPromptButton);
   }

   private boolean isPromptTextAvailable() {
      return !promptField.getText().isBlank();
   }
}
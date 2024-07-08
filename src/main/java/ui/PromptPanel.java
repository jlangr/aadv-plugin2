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
   private final SendPromptListener sendPromptListener;
   private JTextArea promptField;
   private JButton promptButton;

   public PromptPanel(SendPromptListener sendPromptListener) {
      this.sendPromptListener = sendPromptListener;

      createPromptField();
      createPromptButton();

      setLayout(new GridBagLayout());
      var constraints = new GridBagConstraints();

      constraints.fill = GridBagConstraints.BOTH;
      constraints.insets = new Insets(5, 5, 5, 5);

      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.weightx = 1.0;
      constraints.weighty = 0.1;
      constraints.gridwidth = 2;
      add(new JBScrollPane(promptField), constraints);

      constraints.gridx = 2;
      constraints.gridy = 0;
      constraints.weightx = 0.0;
      constraints.gridwidth = 1;
      constraints.fill = GridBagConstraints.NONE;
      add(promptButton, constraints);

      var preferredHeight = UI.calculatePreferredHeight(promptField, PROMPT_FIELD_LINE_COUNT);
      setPreferredSize(new Dimension(400, preferredHeight));
      setMinimumSize(new Dimension(400, preferredHeight));
      setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
   }

   private void createPromptField() {
      promptField = new JTextArea(5, 80);
      promptField.getDocument().addDocumentListener(
         new JTextAreaDocumentListener(this::updateButtonState));
      promptField.setEditable(true);
      promptField.setLineWrap(true);
      promptField.setWrapStyleWord(true);
      promptField.setMargin(new Insets(10, 10, 10, 10));
   }

   private void updateButtonState(DocumentEvent documentEvent) {
      promptButton.setEnabled(!promptField.getText().isBlank());
   }

   private void createPromptButton() {
      promptButton = new JButton(MSG_SEND_PROMPT);
      promptButton.addActionListener( e -> sendPromptListener.send(promptField.getText()));
      promptButton.setEnabled(isPromptTextAvailable());
      setButtonHeight(promptButton);
   }

   private boolean isPromptTextAvailable() {
      return !promptField.getText().isBlank();
   }
}
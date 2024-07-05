package ui;

import com.intellij.ui.components.JBScrollPane;
import javax.swing.*;
import java.awt.*;
import static utils.UI.setButtonHeight;

public class PromptPanel extends JPanel {
   private static final String MSG_KEY_NOT_CONFIGURED = "API key not configured. Please provide it in the AADV plugin settings.";
   private final SendPromptListener sendPromptListener;
   private JTextArea promptField;
   private JButton promptButton;

   public PromptPanel(SendPromptListener sendPromptListener, String apiKey) {
      this.sendPromptListener = sendPromptListener;

      createPromptField();
      createPromptButton();

      if (apiKey == null)
         promptField.setText(MSG_KEY_NOT_CONFIGURED);

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

      setPreferredSize(new Dimension(400, 100));
      setMinimumSize(new Dimension(400, 100));
   }

   private void createPromptField() {
      promptField = new JTextArea(4, 80);
      promptField.setEditable(true);
      promptField.setLineWrap(true);
      promptField.setWrapStyleWord(true);
      promptField.setMargin(new Insets(10, 10, 10, 10));
   }

   private void createPromptButton() {
      promptButton = new JButton("Send");
      promptButton.addActionListener( e -> sendPromptListener.send(promptField.getText()));
      setButtonHeight(promptButton);
   }
}

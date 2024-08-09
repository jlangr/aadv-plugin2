package plugin.settings;

import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import java.awt.*;

public class LLMAPISettingsComponent {
   private final JPanel panel;
   private final JBTextField apiKeyField;

   public LLMAPISettingsComponent(LLMAPISettings llmapiSettings) {
      panel = new JPanel(new GridBagLayout());
      apiKeyField = new JBTextField(llmapiSettings.apiKey()); // Load API key from state

      var gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(5, 5, 5, 5);

      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 0;
      panel.add(new JLabel("OpenAI API Key:"), gbc);

      gbc.gridx = 1;
      gbc.gridy = 0;
      gbc.weightx = 1.0;
      panel.add(apiKeyField, gbc);
   }

   public JPanel getPanel() {
      return panel;
   }

   public boolean isModified() {
      String savedApiKey = AADVSettingsState.getInstance().getApiKey();
      return !apiKeyField.getText().equals(savedApiKey);
   }

   public void apply() {
      AADVSettingsState.getInstance().setApiKey(apiKeyField.getText());
   }

   public void reset() {
      String savedApiKey = AADVSettingsState.getInstance().getApiKey();
      apiKeyField.setText(savedApiKey);
   }
}

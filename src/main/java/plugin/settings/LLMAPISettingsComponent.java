package plugin.settings;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class LLMAPISettingsComponent {
   private final JPanel panel;
   private final JBTextField apiKeyField;

   public LLMAPISettingsComponent() {
      panel = new JPanel(new GridBagLayout());
      apiKeyField = new JBTextField();

      var gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = JBUI.insets(5);

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
      var savedApiKey = OpenAISettingsState.getInstance().getApiKey();
      return !StringUtil.equals(apiKeyField.getText(), savedApiKey);
   }

   public void apply() {
      OpenAISettingsState.getInstance().setApiKey(apiKeyField.getText());
   }

   public void reset() {
      var savedApiKey = OpenAISettingsState.getInstance().getApiKey();
      apiKeyField.setText(savedApiKey);
   }
}

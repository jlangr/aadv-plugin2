package plugin.settings;
import com.intellij.ui.components.JBScrollPane;
import utils.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RuleComponent extends JPanel {
   private static final String MSG_DELETE = "Delete";
   private JTextArea textArea;
   private JButton deleteButton;
   private StyleSettingsComponent parentComponent;
   private Language language;
   private Rule rule;
   private JButton enabledButtonToggle;

   public RuleComponent(Rule rule, Language language, StyleSettings styleSettings, StyleSettingsComponent parentComponent) {
      this.rule = rule;
      this.language = language;
      this.parentComponent = parentComponent;

      setLayout(new BorderLayout());

      // Create JTextArea with the specified number of rows and columns
      textArea = new JTextArea(rule.getText(), 5, 20);
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);

      // Calculate the maximum height based on 5 lines of text
      var fontMetrics = textArea.getFontMetrics(textArea.getFont());
      var lineHeight = fontMetrics.getHeight();
      var maxHeight = lineHeight * 5; // Maximum height for 5 lines of text

      // Set preferred and maximum size for JTextArea
      textArea.setPreferredSize(new Dimension(textArea.getPreferredSize().width, maxHeight));
      textArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));

      showRedBorderWhenDisabled(rule);

      // Wrap JTextArea in a JScrollPane
      var scrollPane = new JBScrollPane(textArea);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

      // Ensure JScrollPane does not grow to fill the parent
      scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));

      // Add the JScrollPane to the center of the panel
      add(scrollPane, BorderLayout.CENTER);

      // Create the button panel and set its maximum size
      var buttonPanel = createButtonPanel();
      buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());

      // Add the button panel to the east of the RuleComponent
      add(buttonPanel, BorderLayout.EAST);

      setupEventListeners(styleSettings);
      updateTextArea();
   }

   private void showRedBorderWhenDisabled(Rule rule) {
      textArea.setBorder(
         rule.isEnabled()
            ? UIManager.getBorder("TextArea.border")
            : BorderFactory.createLineBorder(Color.RED));
   }


   private JPanel createButtonPanel() {
      var panel = new JPanel(new GridLayout(1, 1));

      deleteButton = UI.createIconButton("close_icon.png", MSG_DELETE,
         e -> {
            language.getRules().remove(rule);
            parentComponent.updateUIFromModel();
         });
      panel.add(deleteButton);

      enabledButtonToggle = UI.createIconButton(
         rule.isEnabled() ? "Circle-Pause.png" : "Circle-Play.png",
         rule.isEnabled() ? "Disable" : "Enable",
         e -> {
            language.toggleEnabled(rule);
            parentComponent.updateUIFromModel();
         }
      );
      panel.add(enabledButtonToggle);

      return panel;
   }

   private void setupEventListeners(StyleSettings styleSettings) {
      textArea.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            if (textArea.getText().trim().isEmpty()) {
               language.getRules().remove(rule);
               parentComponent.updateUIFromModel();
            } else {
               int index = language.getRules().indexOf(rule);
               language.getRules().set(index, new Rule(textArea.getText()));
            }
         }
      });
   }

   private void updateTextArea() {
      textArea.setText(rule.getText());
   }
}

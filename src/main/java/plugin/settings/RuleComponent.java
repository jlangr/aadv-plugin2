package plugin.settings;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RuleComponent extends JPanel {

   private JTextArea textArea;
   private JButton deleteButton;
   private StyleSettingsComponent parentComponent;
   private Language language;
   private Rule rule;

   public RuleComponent(Rule rule, Language language, StyleSettings styleSettings, StyleSettingsComponent parentComponent) {
      this.rule = rule;
      this.language = language;
      this.parentComponent = parentComponent;

      setLayout(new BorderLayout());

      textArea = new JTextArea(rule.getText(), 3, 20);
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);
      add(new JBScrollPane(textArea), BorderLayout.CENTER);

      var buttonPanel = createButtonPanel();
      add(buttonPanel, BorderLayout.EAST);

      setupEventListeners(styleSettings);
      updateTextArea();
   }

   private JPanel createButtonPanel() {
      var panel = new JPanel(new GridLayout(1, 1));
      deleteButton = new JButton("D");
      panel.add(deleteButton);
      return panel;
   }

   private void setupEventListeners(StyleSettings styleSettings) {
      deleteButton.addActionListener(e -> {
         language.getRules().remove(rule);
         parentComponent.updateUIFromModel();
      });

      textArea.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            if (textArea.getText().trim().isEmpty()) {
               language.getRules().remove(rule);
               parentComponent.updateUIFromModel();
            } else {
               int index = language.getRules().indexOf(rule);
               language.getRules().set(index, new Rule(textArea.getText(), rule.isEnabled()));
            }
         }
      });
   }

   private void updateTextArea() {
      textArea.setText(rule.getText());
   }
}

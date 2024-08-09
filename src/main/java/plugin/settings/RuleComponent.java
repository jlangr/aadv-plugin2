package plugin.settings;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RuleComponent extends JPanel {

   private Rule rule;
   private JTextArea textArea;
   private JButton deleteButton;
   private JButton disableButton;
   private JButton moveUpButton;
   private JButton moveDownButton;
   private JButton addRuleButton;
   private StyleSettingsComponent parentComponent;

   public RuleComponent(Rule rule, StyleSettingsComponent parentComponent) {
      this.rule = rule;
      this.parentComponent = parentComponent;

      setLayout(new BorderLayout());
      textArea = new JTextArea(rule.text(), 3, 20);
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);
      add(new JBScrollPane(textArea), BorderLayout.CENTER);

      var buttonPanel = createButtonPanel();
      add(buttonPanel, BorderLayout.EAST);

      setupEventListeners();
      updateTextArea();
   }

   private JPanel createButtonPanel() {
      var panel = new JPanel(new GridLayout(1, 5));
      deleteButton = new JButton("D");
      disableButton = new JButton("X");
      moveUpButton = new JButton("↑");
      moveDownButton = new JButton("↓");
      addRuleButton = new JButton("+");
      panel.add(deleteButton);
      panel.add(disableButton);
      panel.add(moveUpButton);
      panel.add(moveDownButton);
      panel.add(addRuleButton);
      return panel;
   }

   private void setupEventListeners() {
      deleteButton.addActionListener(e -> parentComponent.removeRuleComponent(this));
      disableButton.addActionListener(e -> toggleRuleEnabled());
      moveUpButton.addActionListener(e -> parentComponent.moveRuleComponentUp(this));
      moveDownButton.addActionListener(e -> parentComponent.moveRuleComponentDown(this));
      addRuleButton.addActionListener(e -> parentComponent.addNewRuleBelow(this));
      textArea.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            if (textArea.getText().trim().isEmpty()) {
               parentComponent.removeRuleComponent(RuleComponent.this);
            }
         }
      });
   }

   private void toggleRuleEnabled() {
      this.rule = new Rule(rule.text(), !rule.isEnabled());
      updateTextArea();
   }

   private void updateTextArea() {
      if (!rule.isEnabled()) {
         textArea.setText("[x] " + rule.text());
         textArea.setEnabled(false);
      } else {
         textArea.setText(rule.text());
         textArea.setEnabled(true);
      }
   }

   public Rule getRule() {
      return new Rule(textArea.getText(), rule.isEnabled());
   }
}

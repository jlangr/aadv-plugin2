package plugin.settings;

import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StyleSettingsComponent extends JPanel {

   private static final String MSG_ENTER_LANGUAGE = "Enter programming language:";
   private DefaultListModel<String> languageListModel;
   private JBList<String> languageList;
   private JPanel rulePanel;
   private ArrayList<RuleComponent> ruleComponents;

   public StyleSettingsComponent() {
      setLayout(new BorderLayout());

      var leftPanel = createLeftPanel();
      rulePanel = createRulePanel();
      add(leftPanel, BorderLayout.WEST);
      add(new JBScrollPane(rulePanel), BorderLayout.CENTER);

      setupEventListeners();
   }

   private JPanel createLeftPanel() {
      var leftPanel = new JPanel(new BorderLayout());
      languageListModel = new DefaultListModel<>();
      languageList = new JBList<>(languageListModel);
      leftPanel.add(new JBScrollPane(languageList), BorderLayout.CENTER);

      var languageButtonsPanel = new JPanel(new FlowLayout());
      var addLanguageButton = new JButton("+");
      var removeLanguageButton = new JButton("-");
      languageButtonsPanel.add(addLanguageButton);
      languageButtonsPanel.add(removeLanguageButton);
      leftPanel.add(languageButtonsPanel, BorderLayout.SOUTH);

      addLanguageButton.addActionListener(e -> onAddLanguage());
      removeLanguageButton.addActionListener(e -> onRemoveLanguage());

      return leftPanel;
   }

   private JPanel createRulePanel() {
      var panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      ruleComponents = new ArrayList<>();
      return panel;
   }

   private void setupEventListeners() {
      languageList.addListSelectionListener(e -> {
         if (!e.getValueIsAdjusting()) {
            updateRuleList();
         }
      });
   }

   private void onAddLanguage() {
      var language = JOptionPane.showInputDialog(MSG_ENTER_LANGUAGE);
      if (language != null && !language.trim().isEmpty()) {
         languageListModel.addElement(language.trim());
         addNewRule(new Rule("", true));
      }
   }

   private void onRemoveLanguage() {
      var selectedIndex = languageList.getSelectedIndex();
      if (selectedIndex != -1) {
         languageListModel.remove(selectedIndex);
         rulePanel.removeAll();
         ruleComponents.clear();
         rulePanel.revalidate();
         rulePanel.repaint();
      }
   }

   private void updateRuleList() {
      rulePanel.removeAll();
      ruleComponents.clear();
      var selectedLanguage = languageList.getSelectedValue();
      if (selectedLanguage != null) {
         addRuleComponent(new Rule("Rule 1 for " + selectedLanguage, true));
         addRuleComponent(new Rule("Rule 2 for " + selectedLanguage, true));
         addRuleComponent(new Rule("Rule 3 for " + selectedLanguage, true));
      }
      rulePanel.revalidate();
      rulePanel.repaint();
   }

   private void addRuleComponent(Rule rule) {
      var ruleComponent = new RuleComponent(rule, this);
      ruleComponents.add(ruleComponent);
      rulePanel.add(ruleComponent);
   }

   public void removeRuleComponent(RuleComponent ruleComponent) {
      ruleComponents.remove(ruleComponent);
      rulePanel.remove(ruleComponent);
      rulePanel.revalidate();
      rulePanel.repaint();
   }

   public void moveRuleComponentUp(RuleComponent ruleComponent) {
      var index = ruleComponents.indexOf(ruleComponent);
      if (index > 0) {
         ruleComponents.remove(index);
         ruleComponents.add(index - 1, ruleComponent);
         rulePanel.remove(ruleComponent);
         rulePanel.add(ruleComponent, index - 1);
         rulePanel.revalidate();
         rulePanel.repaint();
      }
   }

   public void moveRuleComponentDown(RuleComponent ruleComponent) {
      var index = ruleComponents.indexOf(ruleComponent);
      if (index < ruleComponents.size() - 1) {
         ruleComponents.remove(index);
         ruleComponents.add(index + 1, ruleComponent);
         rulePanel.remove(ruleComponent);
         rulePanel.add(ruleComponent, index + 1);
         rulePanel.revalidate();
         rulePanel.repaint();
      }
   }

   public void addNewRuleBelow(RuleComponent ruleComponent) {
      var index = ruleComponents.indexOf(ruleComponent);
      var newRuleComponent = new RuleComponent(new Rule("", true), this);
      ruleComponents.add(index + 1, newRuleComponent);
      rulePanel.add(newRuleComponent, index + 1);
      rulePanel.revalidate();
      rulePanel.repaint();
   }

   public void addNewRule(Rule rule) {
      var newRuleComponent = new RuleComponent(rule, this);
      ruleComponents.add(newRuleComponent);
      rulePanel.add(newRuleComponent);
      rulePanel.revalidate();
      rulePanel.repaint();
   }
}

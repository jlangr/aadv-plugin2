package plugin.settings;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StyleSettingsComponent extends JPanel {
   private DefaultListModel<String> languageListModel;
   private JBList<String> languageList;
   private JPanel rulePanel;
   private final StyleSettings styleSettings;

   public StyleSettingsComponent() {
      this.styleSettings = AADVSettingsState.getInstance().getStyleSettings();

      setLayout(new BorderLayout());

      var leftPanel = createLeftPanel();
      rulePanel = createRulePanel();

      add(leftPanel, BorderLayout.WEST);
      var scrollableRulePanel = new JBScrollPane(rulePanel);
      scrollableRulePanel.setVerticalScrollBarPolicy(JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      add(scrollableRulePanel, BorderLayout.CENTER);

      setupEventListeners();

      updateUIFromModel();
   }

   private JPanel createLeftPanel() {
      var leftPanel = new JPanel(new BorderLayout());
      languageListModel = new DefaultListModel<>();
      languageList = new JBList<>(languageListModel);
      leftPanel.add(new JScrollPane(languageList), BorderLayout.CENTER);

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

      var addRuleButton = new JButton("Add Rule");
      addRuleButton.addActionListener(e -> onAddRule());

      panel.add(addRuleButton);

      rulePanel = panel;
      return panel;
   }

   private void setupEventListeners() {
      languageList.addListSelectionListener(e -> {
         if (!e.getValueIsAdjusting())
            updateRuleList();
      });
   }

   private void onAddLanguage() {
      var language = JOptionPane.showInputDialog("New programming language:");
      if (language != null && !language.trim().isEmpty()) {
         styleSettings.languages().add(new Language(language.trim(), new ArrayList<>()));
         updateUIFromModel();
      }
   }

   private void onRemoveLanguage() {
      var selectedIndex = languageList.getSelectedIndex();
      if (selectedIndex != -1) {
         styleSettings.languages().remove(selectedIndex);
         updateUIFromModel();
      }
   }

   private void onAddRule() {
      int selectedIndex = languageList.getSelectedIndex();
      if (selectedIndex != -1) {
         var selectedLanguage = styleSettings.languages().get(selectedIndex);
         var newRule = new Rule("New Rule");
         selectedLanguage.getRules().add(newRule);
         updateRuleList();
      }
   }

   private void updateRuleList() {
      rulePanel.removeAll();
      int selectedIndex = languageList.getSelectedIndex();
      if (selectedIndex != -1) {
         var selectedLanguage = styleSettings.languages().get(selectedIndex);
         for (var rule : selectedLanguage.getRules())
            addRuleComponent(rule, selectedLanguage);
      }

      var addRuleButton = new JButton("Add Rule");
      addRuleButton.addActionListener(e -> onAddRule());
      rulePanel.add(addRuleButton);

      rulePanel.revalidate();
      rulePanel.repaint();
   }

   private void addRuleComponent(Rule rule, Language language) {
      if (rulePanel.getComponentCount() > 0)
         rulePanel.add(Box.createVerticalStrut(10));

      var ruleComponent = new RuleComponent(rule, language, styleSettings, this);

      rulePanel.add(ruleComponent, -1);
   }

   public boolean isModified() {
      var currentSettings = AADVSettingsState.getInstance().getStyleSettings();
      return !styleSettings.equals(currentSettings);
   }

   public void apply() {
      AADVSettingsState.getInstance().setStyleSettings(cloneStyleSettings(styleSettings));
   }

   public void reset() {
      var currentSettings = AADVSettingsState.getInstance().getStyleSettings();
      styleSettings.languages().clear();
      styleSettings.languages().addAll(currentSettings.languages());

      updateUIFromModel();
   }

   public void updateUIFromModel() {
      languageListModel.clear();
      for (var language : styleSettings.languages())
         languageListModel.addElement(language.getName());

      if (!styleSettings.languages().isEmpty()) {
         languageList.setSelectedIndex(0); // Ensure a language is selected
         updateRuleList();
      }
      revalidate();
      repaint();
   }

   private StyleSettings cloneStyleSettings(StyleSettings settings) {
      var clonedLanguages = new ArrayList<Language>();
      for (var language : settings.languages()) {
         var clonedRules = new ArrayList<>(language.getRules());
         clonedLanguages.add(new Language(language.getName(), clonedRules));
      }
      return new StyleSettings(clonedLanguages);
   }
}

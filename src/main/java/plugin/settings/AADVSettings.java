package plugin.settings;

import com.intellij.ui.components.JBTabbedPane;

import javax.swing.*;
import java.awt.*;

public class AADVSettings extends JPanel {
    public static final String MSG_LLM_APIS = "LLM APIs";
    public static final String MSG_CODING_STYLE = "Coding Style";
    StyleSettingsComponent styleSettingsComponent = new StyleSettingsComponent();
    LLMAPISettingsComponent llmapiSettingsComponent = new LLMAPISettingsComponent();
    
    public AADVSettings() {
        setLayout(new BorderLayout());
        
        var tabbedPane = new JBTabbedPane();
        
        tabbedPane.addTab(MSG_LLM_APIS, llmapiSettingsComponent.getPanel());
        tabbedPane.addTab(MSG_CODING_STYLE, styleSettingsComponent);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public boolean isModified() {
        return llmapiSettingsComponent.isModified();
    }

    public void apply() {
        llmapiSettingsComponent.apply();
    }

    public void reset() {
        llmapiSettingsComponent.reset();
    }
}
package plugin.settings;

import com.intellij.ui.components.JBTabbedPane;

import javax.swing.*;
import java.awt.*;

public class AADVSettingsComponent extends JPanel {
    public static final String MSG_LLM_APIS = "LLM APIs";
    public static final String MSG_CODING_STYLE = "Coding Style";

    private final StyleSettingsComponent styleSettingsComponent;
    LLMAPISettingsComponent llmapiSettingsComponent;

    public AADVSettingsComponent() {
        setLayout(new BorderLayout());

        this.styleSettingsComponent = new StyleSettingsComponent();
        this.llmapiSettingsComponent = new LLMAPISettingsComponent(AADVSettingsState.getInstance().getLLMAPISettings());

        var tabbedPane = new JBTabbedPane();

        tabbedPane.addTab(MSG_LLM_APIS, llmapiSettingsComponent.getPanel());
        tabbedPane.addTab(MSG_CODING_STYLE, styleSettingsComponent);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public boolean isModified() {
        return styleSettingsComponent.isModified() || llmapiSettingsComponent.isModified();
    }

    public void apply() {
        if (styleSettingsComponent.isModified())
            styleSettingsComponent.apply();
        if (llmapiSettingsComponent.isModified())
            llmapiSettingsComponent.apply();
    }

    public void reset() {
        styleSettingsComponent.reset();
        llmapiSettingsComponent.reset();
    }
}


public boolean isModified() {
return isModified;
}

public void apply() {
OpenAISettingsState.getInstance().setStyleSettings(styleSettings);
isModified = false;
}

public void reset() {
isModified = false;
styleSettings = OpenAISettingsState.getInstance().getStyleSettings();
updateRuleList();
}

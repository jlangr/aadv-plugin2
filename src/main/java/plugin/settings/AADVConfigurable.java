package plugin.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AADVConfigurable implements Configurable {
   private AADVSettings settingsComponent;

   @Nls(capitalization = Nls.Capitalization.Title)
   @Override
   public String getDisplayName() {
      return "AADV Settings";
   }

   @Nullable
   @Override
   public JComponent createComponent() {
      settingsComponent = new AADVSettings();
      return settingsComponent;
   }

   @Override
   public boolean isModified() {
      return settingsComponent != null && settingsComponent.isModified();
   }

   @Override
   public void apply() {
      if (settingsComponent != null) {
         settingsComponent.apply();
      }
   }

   @Override
   public void reset() {
      if (settingsComponent != null) {
         settingsComponent.reset();
      }
   }

   @Override
   public void disposeUIResources() {
      settingsComponent = null;
   }
}

package plugin.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AADVConfigurable implements Configurable {
   private AADVSettingsComponent aadvSettingsComponent;

   @Nls(capitalization = Nls.Capitalization.Title)
   @Override
   public String getDisplayName() {
      return "AADV Settings";
   }

   @Nullable
   @Override
   public JComponent createComponent() {
      aadvSettingsComponent = new AADVSettingsComponent();
      return aadvSettingsComponent;
   }

   @Override
   public boolean isModified() {
      return aadvSettingsComponent != null && aadvSettingsComponent.isModified();
   }

   @Override
   public void apply() {
      if (aadvSettingsComponent != null) {
         aadvSettingsComponent.apply();
      }
   }

   @Override
   public void reset() {
      if (aadvSettingsComponent != null) {
         aadvSettingsComponent.reset();
      }
   }

   @Override
   public void disposeUIResources() {
      aadvSettingsComponent = null;
   }
}

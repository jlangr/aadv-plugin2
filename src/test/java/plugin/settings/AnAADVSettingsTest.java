package plugin.settings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static plugin.settings.AADVSettings.MSG_CODING_STYLE;
import static plugin.settings.AADVSettings.MSG_LLM_APIS;

class AADVSettingsTest {
   private AADVSettings aadvSettings;
   private LLMAPISettingsComponent mockOpenAISettingsComponent;

   @BeforeEach
   void setUp() {
      mockOpenAISettingsComponent = mock(LLMAPISettingsComponent.class);
      aadvSettings = new AADVSettings() {
         {
            llmapiSettingsComponent = mockOpenAISettingsComponent;
         }
      };
   }

   @Test
   void isModified() {
      when(mockOpenAISettingsComponent.isModified()).thenReturn(true);
      assertTrue(aadvSettings.isModified());

      when(mockOpenAISettingsComponent.isModified()).thenReturn(false);
      assertFalse(aadvSettings.isModified());
   }

   @Test
   void apply() {
      aadvSettings.apply();
      verify(mockOpenAISettingsComponent, times(1)).apply();
   }

   @Test
   void reset() {
      aadvSettings.reset();
      verify(mockOpenAISettingsComponent, times(1)).reset();
   }

   @Test
   void overallLayoutEmploysTabbedPane() {
      var components = aadvSettings.getComponents();
      assertEquals(1, components.length);

      var tabbedPane = (JTabbedPane) components[0];
      assertEquals(2, tabbedPane.getTabCount());
   }

   @Test
   void apiSettings() {
      assertEquals(MSG_LLM_APIS, getTabbedPane().getTitleAt(0));
   }

   @Test
   void styleSettings() {
      assertEquals(MSG_CODING_STYLE, getTabbedPane().getTitleAt(1));
   }

   private JTabbedPane getTabbedPane() {
      return (JTabbedPane)aadvSettings.getComponents()[0];
   }
}

package plugin.settings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static plugin.settings.AADVSettingsComponent.MSG_CODING_STYLE;
import static plugin.settings.AADVSettingsComponent.MSG_LLM_APIS;

class AADVSettingsComponentTest {
   private AADVSettingsComponent aadvSettingsComponent;
   private LLMAPISettingsComponent mockOpenAISettingsComponent;

   // TODO

//   @BeforeEach
//   void setUp() {
//      mockOpenAISettingsComponent = mock(LLMAPISettingsComponent.class);
//      aadvSettingsComponent = new AADVSettingsComponent() {
//         {
//            llmapiSettingsComponent = mockOpenAISettingsComponent;
//         }
//      };
//   }
//
//   @Test
//   void isModified() {
//      when(mockOpenAISettingsComponent.isModified()).thenReturn(true);
//      assertTrue(aadvSettingsComponent.isModified());
//
//      when(mockOpenAISettingsComponent.isModified()).thenReturn(false);
//      assertFalse(aadvSettingsComponent.isModified());
//   }
//
//   @Test
//   void apply() {
//      aadvSettingsComponent.apply();
//      verify(mockOpenAISettingsComponent, times(1)).apply();
//   }
//
//   @Test
//   void reset() {
//      aadvSettingsComponent.reset();
//      verify(mockOpenAISettingsComponent, times(1)).reset();
//   }
//
//   @Test
//   void overallLayoutEmploysTabbedPane() {
//      var components = aadvSettingsComponent.getComponents();
//      assertEquals(1, components.length);
//
//      var tabbedPane = (JTabbedPane) components[0];
//      assertEquals(2, tabbedPane.getTabCount());
//   }
//
//   @Test
//   void apiSettings() {
//      assertEquals(MSG_LLM_APIS, getTabbedPane().getTitleAt(0));
//   }
//
//   @Test
//   void styleSettings() {
//      assertEquals(MSG_CODING_STYLE, getTabbedPane().getTitleAt(1));
//   }
//
//   private JTabbedPane getTabbedPane() {
//      return (JTabbedPane) aadvSettingsComponent.getComponents()[0];
//   }
}

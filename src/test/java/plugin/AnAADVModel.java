package plugin;

import llms.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ui.SourcePanel;
import ui.SourcePanelListener;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AnAADVModel {
   AADVModel model = new AADVModel();

   @Nested
   class SourcePanels {
      SourceFile file = new SourceFile(FileType.PROD, "source", "filename");
      private SourcePanelListener sourcePanelListener = mock(SourcePanelListener.class);

      @Test
      void retrievesAddedPanelByFile() {
         model.add(new SourcePanel(file, sourcePanelListener));

         var panel = model.getPanel(file).get();

         assertEquals(panel.getName(), file.fileName());
      }

      @Test
      void returnsEmptyOptionWhenRetrievingNonexistentFile() {
         var optional = model.getPanel(file);

         assertEquals(Optional.empty(), optional);
      }

      @Test
      void removesPanel() {
         var panel = new SourcePanel(file, sourcePanelListener);
         model.add(panel);

         model.remove(panel);

         var optional = model.getPanel(file);
         assertEquals(Optional.empty(), optional);
      }
   }

   @Test
   void addsExamples() {
      model.addExample("123");
      model.addExample("345");

      assertEquals(List.of(
            new Example("123", "", "", true),
            new Example("345", "", "", true)),
         model.getExamples());
   }

   @Test
   void getExampleThrowsWhenNotFound() {
      assertThrows(ExampleNotFoundException.class, () -> model.getExample("nonexistent"));
   }

   @Test
   void updateExampleWith() {
      model.addExample("123");

      model.updateExample("123", "a", "smelt");

      assertEquals(model.getExample("123").text(), "smelt");
      assertEquals(model.getExample("123").name(), "a");
   }

   @Test
   void deletesExample() {
      model.addExample("123");
      model.addExample("456");

      model.deleteExample("123");

      assertEquals(model.getExamples(),
         List.of(new Example("456", "", "")));
   }

   @Test
   void canObtainExampleListObjectAsWell() {
      model.addExample("123");

      var exampleList = model.getExampleList();
      assertEquals(List.of(new Example("123", "", "")), exampleList.getAll());
   }

   @Test
   void togglesEnabledById() {
      model.addExample("123");

      model.toggleEnabled("123");

      var example = model.getExample("123");
      assertFalse(example.isEnabled());
   }

   @Test
   void dumpPromptWritesCombinedPromptToConsole() {
      model.setPromptText("prompt text");
      model.addExample("1");
      model.updateExample("1", "abc", "one two three");

      var result = model.dumpPrompt();

      assertTrue(result.contains("""
         prompt text
         Examples:
         
         name: abc
         one two three
         """), result);
   }
}
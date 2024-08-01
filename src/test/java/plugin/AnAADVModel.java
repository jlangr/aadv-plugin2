package plugin;

import llms.AADVModel;
import llms.Example;
import llms.ExampleNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnAADVModel {
   AADVModel model = new AADVModel();

   @Test
   void addsExamples() {
      model.addExample("123");
      model.addExample("345");

      assertEquals(List.of(
            new Example("123", "a", "hey"),
            new Example("345", "b", "there")),
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

      assertEquals(model.getExample("123").getText(), "smelt");
      assertEquals(model.getExample("123").getName(), "a");
   }

   @Test
   void deletesExample() {
      model.addExample("123");
      model.addExample("456");

      model.deleteExample("123");

      assertEquals(model.getExamples(),
         List.of(new Example("456", "b name", "b")));
   }

   @Test
   void combinesExamplesForPrompt() {
      model.setPromptText("prompt text");
      model.addExample("1");
      model.updateExample("1", "abc", "one two three");

      var result = model.combinedPrompt();

      assertEquals("""
         prompt text
         Examples:
         name: abc
         one two three""", result);
   }


   @Test
   void doesNotIncludeDisabledExamples() {
      model.setPromptText("prompt text");
      model.addExample("1");
      model.updateExample("1", "abc", "");
      model.addExample("2");
      model.updateExample("2", "def", "");

      model.toggleEnabled("1");

      var result = model.combinedPrompt();
      assertEquals("""
         prompt text
         Examples:
         name: def
         """, result);
   }
}
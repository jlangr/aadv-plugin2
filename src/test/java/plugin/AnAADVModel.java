package plugin;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnAADVModel {
   AADVModel model = new AADVModel();

   @Test
   void addsExamples() {
      model.addExample("123", "hey");
      model.addExample("345", "there");

      assertEquals(List.of(
            new Example("123", "hey"),
            new Example("345", "there")),
         model.getExamples());
   }

   @Test
   void getExampleThrowsWhenNotFound() {
      assertThrows(ExampleNotFoundException.class, () -> model.getExample("nonexistent"));
   }

   @Test
   void updatesExampleText() {
      model.addExample("123", "hey");

      model.updateExample("123", "smelt");

      assertEquals(model.getExampleText("123"), "smelt");
   }

   @Test
   void deletesExample() {
      model.addExample("123", "a");
      model.addExample("456", "b");

      model.deleteExample("123");

      assertEquals(model.getExamples(),
         List.of(new Example("456", "b")));
   }
}
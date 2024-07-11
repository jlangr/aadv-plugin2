package llms;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnExampleList {
   ExampleList examples;

   @Nested
   class Concatenate {
      @Test
      void withoutName() {
         examples = new ExampleList(
            new Example("A", "text a"),
            new Example("B", "text b"),
            new Example("C", "text c"));

         var concatenated = examples.concatenate();

         assertEquals("""
            text a
            ---
            text b
            ---
            text c""", concatenated);
      }

      @Test
      void withName() {
         examples = new ExampleList(
            new Example("A", "text a", "description for a"),
            new Example("B", "text b", "description for b"),
            new Example("C", "text c", "description for c"));

         var concatenated = examples.concatenate();

         assertEquals("""
            Example: description for a
            text a
            ---
            Example: description for b
            text b
            ---
            Example: description for c
            text c""", concatenated);
      }
   }
}
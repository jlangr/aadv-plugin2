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
            new Example("A", "", "text a"),
            new Example("B", "", "text b"),
            new Example("C", "", "text c"));

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
            name: text a
            description for a
            ---
            name: text b
            description for b
            ---
            name: text c
            description for c""", concatenated);
      }

      @Test
      void ignoresDisabledExamples() {
         var exampleB = new Example("B", "", "text b");
         exampleB.toggleEnabled();
         examples = new ExampleList(
            new Example("A", "", "text a"),
            exampleB,
            new Example("C", "", "text c"));

         var concatenated = examples.concatenate();

         assertEquals("""
            text a
            ---
            text c""", concatenated);
      }
   }
}
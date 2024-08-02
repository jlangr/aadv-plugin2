package llms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AnExampleList {
   ExampleList examples;

   @Nested
   class Creation {
      @Test
      void withAListOfExamples() {
         examples = new ExampleList(new Example("1", "x", "y"));

         assertEquals(List.of(new Example("1", "x", "y")), examples.getAll());
      }
   }

   @Nested
   class WhenMutated {
      @BeforeEach
      void setup() {
         examples = new ExampleList();
      }

      @Test
      void retrievesPersistedExample() {
         examples.add("1", "name", "text");

         assertEquals(new Example("1", "name", "text"), examples.get("1"));
      }

      @Test
      void throwsWhenRetrievingNonexistentExample() {
         assertThrows(ExampleNotFoundException.class, () -> examples.get("1"));
      }

      @Test
      void returnsOptionalOnGetOptional() {
         examples.add("1", "name", "text");

         assertEquals(Optional.of(new Example("1", "name", "text")),
            examples.getOptional("1"));
      }

      @Test
      void returnsEmptyOptionalWhenNotFound() {
         assertEquals(Optional.empty(), examples.getOptional("1"));
      }

      @Test
      void deletesExampleById() {
         examples.add("1", "name", "text");

         examples.delete("1");

         assertEquals(Collections.emptyList(), examples.getAll());
      }

      @Test
      void deleteDoesNothingOnEmptyList() {
         examples.delete("1");

         assertEquals(Collections.emptyList(), examples.getAll());
      }

      @Test
      void deleteDoesNothingWhenExampleNotFound() {
         examples.add("1", "name", "text");
         examples.add("2", "name", "text");

         examples.delete("3");

         assertEquals(List.of(new Example("1", "name", "text"), new Example("2", "name", "text")), examples.getAll());
      }

      @Test
      void replacesTextAndNameOnUpdate() {
         examples.add("1", "name", "text");

         examples.update("1", "newName", "newText");

         assertEquals(new Example("1", "newName", "text"), examples.get("1"));
      }

      @Test
      void throwsOnUpdateForNonexistentId() {
         assertThrows(ExampleNotFoundException.class,
            () -> examples.update("1", "", ""));
      }

      @Test
      void togglesEnabledFlag() {
         examples.add("1", "name", "text");

         examples.toggleEnabled("1");

         var example = examples.get("1");
         assertFalse(example.isEnabled());
      }

      @Test
      void toggleEnabledDoesNothingWhenExampleDoesNotExist() {
         examples.add("1", "name", "text");

         examples.toggleEnabled("bad id");

         var example = examples.get("1");
         assertTrue(example.isEnabled());
      }
   }

   @Nested
   class ToPromptText {
      @Test
      void withoutName() {
         examples = new ExampleList(
            new Example("A", "", "text a"),
            new Example("B", "", "text b"),
            new Example("C", "", "text c"));

         var concatenated = examples.toPromptText();

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

         var concatenated = examples.toPromptText();

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

         var concatenated = examples.toPromptText();

         assertEquals("""
            text a
            ---
            text c""", concatenated);
      }
   }
}
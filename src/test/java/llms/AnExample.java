package llms;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnExample {
   @Test
   void createsPromptStringWhenOnlyExampleTextProvided() {
      var example = new Example("1", "", "some text");

      var result = example.toPromptString();

      assertEquals("some text", result);
   }

   @Test
   void createsPromptStringWhenExampleTextAndNameProvided() {
      var example = new Example("1", "my name", "some text");

      var result = example.toPromptString();

      assertEquals("name: my name\nsome text", result);
   }

   @Nested
   class IsEnabled {
      @Test
      void isEnabledByDefault() {
         var example = new Example("1", "", "some text");
         assertTrue(example.isEnabled());
      }

      @Test
      void togglesToNotEnabledAsFirstStep() {
         var example = new Example("1", "", "some text");

         example.toggleEnabled();

         assertFalse(example.isEnabled());
      }

      @Test
      void togglesBackToEnabled() {
         var example = new Example("1", "", "some text");
         example.toggleEnabled();

         example.toggleEnabled();

         assertTrue(example.isEnabled());
      }
   }
}
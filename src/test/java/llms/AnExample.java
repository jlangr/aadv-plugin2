package llms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnExample {
   @Test
   void createsPromptStringWhenOnlyExampleTextProvided() {
      var example = new Example("1", "", "some text");

      var result = example.toPromptText();

      assertEquals("some text", result);
   }

   @Test
   void createsPromptStringWhenExampleTextAndNameProvided() {
      var example = new Example("1", "my name", "some text");

      var result = example.toPromptText();

      assertEquals("name: my name\nsome text", result);
   }

   @Test
   void doesNotAppendNameIfNull() {
      var example = new Example("1", null, "some text");

      var result = example.toPromptText();

      assertEquals("some text", result);
   }

   @Test
   void isEnabledByDefault() {
      var example = new Example("1", "", "some text");
      assertTrue(example.isEnabled());
   }
}
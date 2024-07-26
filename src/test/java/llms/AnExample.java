package llms;

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
}
package llms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static llms.Prompt.*;
import static llms.PromptMessageType.SYSTEM;
import static llms.PromptMessageType.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class APrompt {
   @Test
   void returnsListOfPromptMessages() {
      var prompt = new Prompt("text", new ExampleList(new Example("1", "name", "example text")));

      var result = prompt.messages();

      Assertions.assertEquals(new PromptMessage(SYSTEM, PROMPT_ASSISTANT_GUIDELINES), result.get(0));
      assertEquals(new PromptMessage(SYSTEM, PROMPT_CODE_STYLE), result.get(1));
      assertEquals(new PromptMessage(SYSTEM, PROMPT_LANGUAGE_SPECIFIC_CODE_STYLE), result.get(2));
      var userMessage = result.get(3);
      assertTrue(userMessage.text().contains("""
         text
         Examples:
         
         name: name
         example text
         """), userMessage.text());
      assertEquals(USER, result.get(3).promptMessageType());
   }
}
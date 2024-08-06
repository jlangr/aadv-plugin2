package llms.openai;

import llms.ExampleList;
import llms.Prompt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnOpenAI {
   @InjectMocks
   OpenAI openAI;

   @Mock
   OpenAIClient client;

   @Test
   void retrieveCompletionExtractsSourceFilesFromResponse() {
      var prompt = new Prompt("text", new ExampleList());
      var response = new ChatCompletionResponseBuilder().addChoice("""
         /* prod class FizzBuzz.java */
         class FizzBuzz {}
         /* end prod class */
         """).build();
      when(client.retrieveCompletion(any(HashMap.class))).thenReturn(response);

      var files = openAI.retrieveCompletion(prompt);

      var prodSource = files.prodFiles().get(0).source();
      assertEquals("class FizzBuzz {}", prodSource);
   }
}
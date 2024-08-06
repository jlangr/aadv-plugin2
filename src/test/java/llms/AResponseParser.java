package llms;

import com.fasterxml.jackson.core.JsonProcessingException;
import llms.openai.ResponseParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AResponseParser {
   ResponseParser responseParser;

   @Test
   void canParseResponse() throws JsonProcessingException {
      var jsonResponse = """
         {
           "id": "chatcmpl-9gJW1s3HGlvc6SN1X85rBati110PG",
           "object": "chat.completion",
           "created": 1719871821,
           "model": "gpt-4o-2024-05-13",
           "choices": [
             {
               "index": 0,
               "message": {
                 "role": "assistant",
                 "content": "```java\\nimport static org.junit.jupiter.api.Assertions.assertEquals;\\nimport org.junit.jupiter.api.Test;\\n\\npublic class FizzBuzzTest {\\n\\n    public String fizzBuzz(int number) {\\n        if (number % 15 == 0) {\\n            return \\"FizzBuzz\\";\\n        } else if (number % 3 == 0) {\\n            return \\"Fizz\\";\\n        } else if (number % 5 == 0) {\\n            return \\"Buzz\\";\\n        }\\n        return String.valueOf(number);\\n    }\\n\\n    @Test\\n    public void testFizzBuzz() {\\n        assertEquals(\\"1\\", fizzBuzz(1));\\n        assertEquals(\\"2\\", fizzBuzz(2));\\n        assertEquals(\\"Fizz\\", fizzBuzz(3));\\n        assertEquals(\\"4\\", fizzBuzz(4));\\n        assertEquals(\\"Buzz\\", fizzBuzz(5));\\n        assertEquals(\\"Fizz\\", fizzBuzz(6));\\n        assertEquals(\\"7\\", fizzBuzz(7));\\n        assertEquals(\\"8\\", fizzBuzz(8));\\n        assertEquals(\\"Fizz\\", fizzBuzz(9));\\n        assertEquals(\\"Buzz\\", fizzBuzz(10));\\n        assertEquals(\\"11\\", fizzBuzz(11));\\n        assertEquals(\\"Fizz\\", fizzBuzz(12));\\n        assertEquals(\\"13\\", fizzBuzz(13));\\n        assertEquals(\\"14\\", fizzBuzz(14));\\n        assertEquals(\\"FizzBuzz\\", fizzBuzz(15));\\n        assertEquals(\\"16\\", fizzBuzz(16));\\n    }\\n}\\n```"
               },
               "logprobs": null,
               "finish_reason": "stop"
             }
           ],
           "usage": {
             "prompt_tokens": 56,
             "completion_tokens": 296,
             "total_tokens": 352
           },
           "system_fingerprint": "fp_ce0793330f"
         }
          """;
      var parse = new ResponseParser().parse(jsonResponse);

      assertTrue(parse.firstMessageContent().contains("import static org.junit.jupiter.api.Assertions.assertEquals;"));
   }
}